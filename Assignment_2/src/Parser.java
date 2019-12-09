import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.util.ArrayList;
import java.util.List;

/**
 * This class parses a file adding it into a graph
 */
public class Parser {

    /**
     * This method takes a path as a argument and reads the csv file into a graph.
     * Uses packages apache commons and json simple for ease of reading the files into the graph
     *
     * @param path   - a string representation of a path that leads to a csv of actor data
     * @return graph - a graph containing their actors and all of their costars
     */
    public static Graph parse(String path) {
        Graph graph = new Graph();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            JSONParser parser = new JSONParser();
            for (CSVRecord csvr : csvParser) {
                JSONArray array = (JSONArray) parser.parse(csvr.get(2));
                List<String> actors = new ArrayList<>();
                for (Object object : array) {
                    JSONObject obj = (JSONObject) object;
                    actors.add((String) obj.get("name"));
                }
                toGraph(actors, graph);
            }
        } catch (Exception e) {
            System.out.println("An error occurred trying to read in the file");
        }
        return graph;
    }

    /**
     * This private method takes a list of actors from a singular film and adds them to the
     * graph actor by actor with each actor pointing to his costars.
     *
     * @param graph - a graph to add the actors and their costars to
     */
    private static void toGraph(List<String> actors, Graph graph){
        for (String actor1 : actors) {
            for (String actor2 : actors) {
                if (actor1 != actor2){
                    graph.add(actor1.toLowerCase(), actor2.toLowerCase());
                }
            }
        }
    }
}
