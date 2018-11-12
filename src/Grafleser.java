import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Grafleser {
    public ArrayList<Node> nodeList = new ArrayList<>();
    public ArrayList<Node> kantList = new ArrayList<>();
    public Grafleser(String nodefil, String kantfil){
        try{
            BufferedReader br = new BufferedReader(new FileReader(nodefil));
            String line = br.readLine(); //Første er antNoder, antKanter
            String[] splut = line.split(" ");
            int nodeAntall = Integer.parseInt(splut[0]);
            int kantAntall = Integer.parseInt(splut[1]);

            line = br.readLine();
            while (line != null){
                //nodeListe.add(new Node(i);
                //System.out.println(line);
                String[] split = line.trim().split(" ");
                Node startNode = new Node(split[0]);
                Node endNode = new Node(split[1]);

                //Sjekker om noder finnes el. ikke
                int startIndex = nodeList.indexOf(startNode);
                if(startIndex < 0){
                    nodeList.add(startNode);
                }
                else{
                    startNode = nodeList.get(startIndex);
                }

                int endIndex = nodeList.indexOf(endNode);
                if(endIndex < 0){
                    nodeList.add(endNode);
                }
                else{
                    endNode = nodeList.get(endIndex);
                }

                Edge edge = new Edge(startNode, endNode, Integer.parseInt(split[2]));
                startNode.edgeList.add(edge);

                line = br.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }
}
