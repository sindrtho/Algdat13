import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class Dijkstra {
    ArrayList<Node> nodeList = new ArrayList<>();
    public Dijkstra(Grafleser leser){
        this.nodeList = (ArrayList<Node>) leser.nodes.clone();
//         this.nodeList = (ArrayList<Node>) leser.nodeList.clone();
    }
    int teller = 0;

    public String run(int startIndex, int endIndex){
        ArrayList<Node> prio = new ArrayList<>(); //Bedre prio-kø: Heap
        Node start = nodeList.get(startIndex);
        start.dist = 0;
        prio.add(start);

        ArrayList<Node> visited = new ArrayList<>();
        while(prio.size() > 0){
            Collections.sort(prio);
            Node n = prio.get(0); //Den øverste
            if(!n.visited){ //if it's not been visited before
                for(Edge e : n.edgeList){ //Legge inn ny distanse hvis det er kortere enn det de er allerede
                    if(!e.endNode.visited) {
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
                n.visited = true;
            }
            prio.remove(n);
            teller++;
        }

//        Node node = nodeList.get(startIndex);
        nodeList = sortNodeListByNumber(nodeList);
        String res = ""; //"N: Node, A: Ancestor, L: minLength\nStartNode: " + node.name + "\nN | A | L\n";
//        for(Node n : nodeList){
//            res += n.name + " | " +  (n.ancestor != null ? n.ancestor.name : "§") + " | " + n.dist + "\n";
//        }
        res += "Antall noder tatt ut av køen: " + teller;
        Node endNode = nodeList.get(endIndex);
        return res + "\nkortest distanse = " + endNode.dist;
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