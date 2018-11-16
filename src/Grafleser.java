import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Grafleser {

    public ArrayList<Node> nodes;
    public ArrayList<Edge> edges;

    public Grafleser(boolean url, String nodefile, String edgefile){
        if(!url) {
            try (
                    BufferedReader nodeBr = new BufferedReader(new FileReader(nodefile));
                    BufferedReader edgeBr = new BufferedReader(new FileReader(edgefile));
            ) {
                Long startTime = System.currentTimeMillis();

                StringTokenizer st = new StringTokenizer(nodeBr.readLine());
                int nodeCount = Integer.parseInt(st.nextToken());   //Number of total nodes in the grid.

                st = new StringTokenizer(edgeBr.readLine());
                int edgeCount = Integer.parseInt(st.nextToken());   //Number of total edges between nodes.

                nodes = new ArrayList<>();
                edges = new ArrayList<>();

                Long startReadingNodes = System.currentTimeMillis();

                //Add all nodes from the file to a ArrayList<Node>.
                for (int i = 0; i < nodeCount; i++) {
                    st = new StringTokenizer(nodeBr.readLine());
                    nodes.add(new Node(st.nextToken(), st.nextToken(), st.nextToken()));
                }

                Long nodeReadEnd = System.currentTimeMillis();

                System.out.println("Time to read Nodes: " + ((nodeReadEnd - startReadingNodes) / 1000.0) + "sec");

                Long startReadingEdges = System.currentTimeMillis();

                //Add all edges from file to a ArrayList<Edge> as well as adding all the edges to their start node edge list.
                for (int i = 0; i < edgeCount; i++) {
                    st = new StringTokenizer(edgeBr.readLine());
                    Node start = nodes.get(Integer.parseInt(st.nextToken()));
                    Node next = nodes.get(Integer.parseInt(st.nextToken()));
                    Edge newEdge = new Edge(start, next, Integer.parseInt(st.nextToken()));
                    edges.add(newEdge);
                    start.edgeList.add(newEdge);
                }

                Long endOfReadingEdges = System.currentTimeMillis();

                Long endTime = System.currentTimeMillis();

                System.out.println("Time to Read Edges: " + (endOfReadingEdges - startReadingEdges) / 1000 + "sec");
                System.out.println("Time to Read Everything: " + ((endTime - startTime) / 1000) + "sec");

                System.out.println("\nNumber of nodes: " + nodes.size());
                System.out.println("\nNumber of edges: " + edges.size());

                if (nodes.size() < 1000) {
                    System.out.println("\nNodes: ");
                    for (Node n : nodes)
                        System.out.println(n);
                }
                if (edges.size() < 1000) {
                    System.out.println("\nEdges: ");
                    for (Edge e : edges)
                        System.out.println(e);
                }
                System.out.println("yo");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }else{
            Scanner sNode = null;
            Scanner sEdge = null;
            try{
                URL nodeUrl = new URL(nodefile);
                sNode = new Scanner(nodeUrl.openStream());
                URL edgeUrl = new URL(edgefile);
                sEdge= new Scanner(edgeUrl.openStream());

                Long startTime = System.currentTimeMillis();

                StringTokenizer st = new StringTokenizer(sNode.nextLine());
                int nodeCount = Integer.parseInt(st.nextToken());   //Number of total nodes in the grid.

                st = new StringTokenizer(sEdge.nextLine());
                int edgeCount = Integer.parseInt(st.nextToken());   //Number of total edges between nodes.

                nodes = new ArrayList<>();
                edges = new ArrayList<>();

                Long startReadingNodes = System.currentTimeMillis();

                //Add all nodes from the file to a ArrayList<Node>.
                for (int i = 0; i < nodeCount; i++) {
                    st = new StringTokenizer(sNode.nextLine());
                    nodes.add(new Node(st.nextToken(), st.nextToken(), st.nextToken()));
                }

                Long nodeReadEnd = System.currentTimeMillis();

                System.out.println("Time to read Nodes: " + ((nodeReadEnd - startReadingNodes) / 1000.0) + "sec");

                Long startReadingEdges = System.currentTimeMillis();

                //Add all edges from file to a ArrayList<Edge> as well as adding all the edges to their start node edge list.
                for (int i = 0; i < edgeCount; i++) {
                    st = new StringTokenizer(sEdge.nextLine());
                    Node start = nodes.get(Integer.parseInt(st.nextToken()));
                    Node next = nodes.get(Integer.parseInt(st.nextToken()));
                    Edge newEdge = new Edge(start, next, Integer.parseInt(st.nextToken()));
                    edges.add(newEdge);
                    start.edgeList.add(newEdge);
                }

                Long endOfReadingEdges = System.currentTimeMillis();

                Long endTime = System.currentTimeMillis();

                System.out.println("Time to Read Edges: " + (endOfReadingEdges - startReadingEdges) / 1000 + "sec");
                System.out.println("Time to Read Everything: " + ((endTime - startTime) / 1000) + "sec");

                System.out.println("\nNumber of nodes: " + nodes.size());
                System.out.println("\nNumber of edges: " + edges.size());

                /*
                if (nodes.size() < 1000) {
                    System.out.println("\nNodes: ");
                    for (Node n : nodes)
                        System.out.println(n);
                }
                if (edges.size() < 1000) {
                    System.out.println("\nEdges: ");
                    for (Edge e : edges)
                        System.out.println(e);
                }*/
                System.out.println("yo");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                sNode.close();
                sEdge.close();
            }
        }
    }
}
