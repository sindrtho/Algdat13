import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Dijkstra {
    ArrayList<Node> nodeList = new ArrayList<>();
    public Dijkstra(Grafleser leser){
        this.nodeList = leser.nodeList;
    }
    int teller = 0;

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
                        int newDist = n.dist + e.time;
                        if (newDist < e.endNode.dist || e.endNode.dist == -1) {
                            e.endNode.dist = newDist;
                            e.endNode.ancestor = n;
                        }
                    }
                }
                visited.add(n);
            }
            prio.remove(n);
            teller++;
        }

        Node node = nodeList.get(startNode);
        nodeList = sortNodeListByNumber(nodeList);
        System.out.println( "N: Node, A: Ancestor, L: minLength\nStartNode: " + node.name + "\nN | A | L");
        for(Node n : nodeList){
            System.out.println(n.name + " | " +  (n.ancestor != null ? n.ancestor.name : "§") + " | " + n.dist);
        }
        System.out.println("Antall noder tatt ut av køen: " + teller);
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

    protected ArrayList<Node> sortNodeListByNumber(ArrayList<Node> list){
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
}