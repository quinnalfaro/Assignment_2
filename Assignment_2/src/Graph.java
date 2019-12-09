import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Queue;


public class Graph {
    HashMap<String, HashSet<String>> graph = new HashMap<String, HashSet<String>>();

    /**
     * Graph constructor
     */
    public Graph() {
        this.graph = new HashMap<String, HashSet<String>>();

    }

    /**
     * This method adds an actor if they don't exist in the graph as a key and then adds their costar
     * into a set attached to their name. (Hash set for speed, no repeat values, and because it needs not
     * be ordered)
     *
     * @param actor1 - the actor to add to as the key
     * @param actor2 - the actor to add as the costar of actor one into a set
     */
    public void add(String actor1, String actor2) {
        graph.putIfAbsent(actor1, new HashSet<>());
        graph.get(actor1).add(actor2);
    }

    /**
     * This searches for an the connection between two actors performing a depth first search. It builds a disjoint and
     * upon the discovery of the second actor uses the private helper method getPath to turn the disjoint set into an
     * array list which it then returns
     *
     * @param actor1 - the actor
     * @param actor2 - the actor to check to find the connection with actor2
     * @return ArrayList<String> - an arraylist of the connection
     */
    public ArrayList<String> search(String actor1, String actor2) {
        Queue<String> queue = new LinkedList<String>();
        HashMap<String, String> disjointSet = new HashMap<String, String>();
        ArrayList<String> visited = new ArrayList<String>();
        disjointSet.put(actor1, null);
        visited.add(actor1);
        queue.add(actor1);

        while (!queue.isEmpty()) {
            String curr = queue.poll();
            HashSet<String> edges = graph.get(curr);
            for (String e : edges) {
                if (!visited.contains(e)) {
                    queue.add(e);
                    visited.add(e);
                    disjointSet.put(e, curr);
                    if (e.equals(actor2)) {
                        return getPath(disjointSet, actor2);
                    }
                }
            }
        }
        return getPath(disjointSet, actor2);
    }

    /**
     * This helper method takes a disjoint set built with a hashmap and actor and extracts a path
     * from the the head and tail of the disjoint set
     *
     * @param map    - a string representation of a path that leads to a csv of actor data
     * @param actor2 - a string representation of a path that leads to a csv of actor data
     * @return path  - the path between the two actors
     */
    public ArrayList<String> getPath(HashMap<String, String> map, String actor2) {
        ArrayList<String> path = new ArrayList<>();
        while (map.get(actor2) != null) {
            path.add(0, actor2);
            actor2 = map.get(actor2);
        }
        path.add(0, actor2);
        return path;
    }

    /**
     * This method checks the graph for a actor as a key in the graph
     *
     * @param actor - the actor to check for in the graph
     * @return true - if the actor is in the graph
     */
    public boolean contains(String actor){
        return graph.containsKey(actor);
    }
}