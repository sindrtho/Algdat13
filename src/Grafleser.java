import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Grafleser {
    public ArrayList<Node> nodeList = new ArrayList<>();
    public ArrayList<Edge> edgeList = new ArrayList<>();

    public Grafleser(String nodefile, String edgefile){
        try(
            BufferedReader nodeBr = new BufferedReader(new FileReader(nodefile));
            BufferedReader edgeBr = new BufferedReader(new FileReader(edgefile));
        )
        {
            String line = null;
            line = nodeBr.readLine();
            int nodeCount = Integer.parseInt(line);
            line = edgeBr.readLine();
            int edgeCount = Integer.parseInt(line);

            line = nodeBr.readLine();
            while (line != null){
                //System.out.println(line);
                String[] split = line.trim().split(" ");
                Node node = new Node(split[0], split[1], split[2]); //nr, kord1, kord2
                nodeList.add(node);

                line = nodeBr.readLine();
            }
            line = edgeBr.readLine();
            while (line != null){
                //System.out.println(line);
                String[] split = line.trim().split(" "); //from, to, time, length, speedlimit
                Node start = nodeList.get(nodeList.indexOf(new Node(split[0])));
                Node end = nodeList.get(nodeList.indexOf(new Node(split[1])));

                Edge edge = new Edge(start, end, Integer.parseInt(split[2]), Integer.parseInt(split[3]), Integer.parseInt(split[4]));
                edgeList.add(edge);
                start.edgeList.add(edge);

                line = edgeBr.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }
}
