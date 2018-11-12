import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Dijkstra {
    public static void main(String[] args) {
        String filnavn = "testfile";
        Dijkstra dijkstra = new Dijkstra(filnavn);
        dijkstra.run(0);
    }

    ArrayList<Node> nodeList = new ArrayList<>();
    public Dijkstra(String filnavn){
        try{
            BufferedReader br = new BufferedReader(new FileReader(filnavn));
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
        new ListeUtskriver<Node>().utskrift("***NodeList***\n", nodeList, true);
    }

    public void run(int startNode){
        ArrayList<Node> prio = new ArrayList<>(); //Bedre prio-kø: Heap
        Node start = nodeList.get(startNode);
        start.dist = 0;
        prio.add(start);

        ArrayList<Node> visited = new ArrayList<>();
        while(prio.size() > 0){
            sortNodeList(prio);
            Node n = prio.get(0); //Den øverste
            if(!visited.contains(n)){ //if it's not been visited before
                for(Edge e : n.edgeList){ //Legge inn ny distanse hvis det er kortere enn det de er allerede
                    if(!visited.contains(e.endNode)) {
                        if(!prio.contains(e.endNode)){
                            prio.add(e.endNode);
                        }
                        int newDist = n.dist + e.weight;
                        if (newDist < e.endNode.dist || e.endNode.dist == -1) {
                            e.endNode.dist = newDist;
                            e.endNode.ancestor = n;
                        }
                    }
                }
                visited.add(n);
            }
            prio.remove(n);
        }

        Node node = nodeList.get(startNode);
        nodeList = sortNodeListByNumber(nodeList);
        System.out.println( "N: Node, A: Ancestor, L: minLength\nStartNode: " + node.name + "\nN | A | L");
        for(Node n : nodeList){
            System.out.println(n.name + " | " +  (n.ancestor != null ? n.ancestor.name : "§") + " | " + n.dist);
        }
    }

    void sortNodeList(ArrayList<Node> liste){
        for(int i = 0; i < liste.size(); i++){
            for(int j = i; j < liste.size(); j++){
                if(liste.get(i).dist > liste.get(j).dist){
                    Node temp = liste.get(i);
                    liste.set(i, liste.get(j));
                    liste.set(j, temp);
                }
            }
        }
    }

    private ArrayList<Node> sortNodeListByNumber(ArrayList<Node> list){
        ArrayList<Node> sort = new ArrayList<>();
        Node min = null;
        while(list.size() > 0){
            for(Node n : list){
                if(min == null){
                    min = n;
                }
                else if(Integer.parseInt(n.name) < Integer.parseInt(min.name)){
                    min = n;
                }
            }
            list.remove(min);
            sort.add(min);
            min = null;
        }
        return sort;
    }

    private class Node{
        public String name;
        public ArrayList<Edge> edgeList = new ArrayList<>();
        public Node ancestor = null;
        public int dist = -1;
        public Node(String name){
            this.name = name;
        }

        @Override
        public String toString() {
            String res = name + "(" + dist + "): ";
            for(Edge e : edgeList){
                res += e.toString() + ", ";
            }
            return res;
        }
        @Override
        public boolean equals(Object o) {
            if(o == this){
                return true;
            }
            if(!o.getClass().equals(this.getClass())){
                return false;
            }
            Node n = (Node) o;
            return n.name.equals(this.name);
        }
    }

    private class Edge{
        public int weight;
        public Node startNode;
        public Node endNode;
        public Edge(Node startNode, Node endNode, int weight){
            this.weight = weight;
            this.startNode = startNode;
            this.endNode = endNode;
        }

        @Override
        public String toString() {
            String res = startNode.name + " - " + endNode.name + ": " + weight;
            return res;
        }
        @Override
        public boolean equals(Object o) {
            if(o == this){
                return true;
            }
            if(!o.getClass().equals(this.getClass())){
                return false;
            }
            Edge n = (Edge) o;
            return n.startNode.equals(this.startNode) && n.endNode.equals(this.startNode);
        }
    }

    private class ListeUtskriver<T>{
        public void utskrift(String msg, ArrayList<T> liste, boolean newLine){
            String res = msg;
            if(newLine){
                for(int i = 0; i < liste.size(); i++){
                    res += liste.get(i).toString() + "\n";
                }
                System.out.println(res);
            }
            else{
                for(int i = 0; i < liste.size(); i++){
                    res += liste.get(i).toString() + ", ";
                }
                System.out.println(res + "\n");
            }
        }
    }
}