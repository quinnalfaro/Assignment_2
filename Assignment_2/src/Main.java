import java.util.ArrayList;
import java.util.*;

/**
 * Main Method. First builds a graph containing the actors and every other
 * actor they have been in a film with, and then prompts the user for two actors
 * and finally checks and returns a path that connects the two actors.
 *
 * @param args, the first is a path that leads to a csv of actor data
 */
public class Main {
    public static void main(String[] args) {
        Graph graph = Parser.parse(args[0]);
        Scanner scan = new Scanner(System.in);
        String actor1 = null;
        String actor2 = null;

        while(actor1 == null || !graph.contains(actor1)){
            System.out.println("First Actor: ");
            actor1 = scan.nextLine().toLowerCase();
            if (actor1 == null || !graph.contains(actor1)){
                System.out.println("Actor not in database. Please reenter!");
            }
        }

        while(actor2 == null || !graph.contains(actor2)){
            System.out.println("Second Actor: ");
            actor2 = scan.nextLine().toLowerCase();
            if (actor2 == null || !graph.contains(actor2)){
                System.out.println("Actor not in database. Please reenter!");
            }
        }

        ArrayList<String> path = graph.search(actor1.toLowerCase(), actor2.toLowerCase());

        if (path.isEmpty()){
            System.out.println("No path between actors");
        } else{
            System.out.println("Path between actors: " + path);
        }
    }
}
