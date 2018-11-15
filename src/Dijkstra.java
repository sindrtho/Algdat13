import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra {
    ArrayList<Node> nodeList;


    public Dijkstra(Grafleser leser){
        this.nodeList = leser.nodes;
//         this.nodeList = (ArrayList<Node>) leser.nodeList.clone();
    }
    int teller = 0;

    public String run(int startIndex, int endIndex){
//        ArrayList<Node> prio = new ArrayList<>(); //Bedre prio-kø: Heap
        PriorityQueue<Node> queue = new PriorityQueue(new MyComparator());
        Node start = nodeList.get(startIndex);
        Node end = nodeList.get(endIndex);
        start.dist = 0;
//        prio.add(start);
        queue.add(start);


        while(!queue.isEmpty()){
            //Collections.sort(prio);
            Node n = queue.poll(); //Den øverste
            if(n.equals(end)){
                break;
            }
            if(!n.visited){ //if it's not been visited before
                for(Edge e : n.edgeList){ //Legge inn ny distanse hvis det er kortere enn det de er allerede
                    if(!e.endNode.visited) {
                        if(!queue.contains(e.endNode)){
                            queue.add(e.endNode);
                        }
                        int newDist = n.dist + e.time;
                        if (newDist < e.endNode.dist || e.endNode.dist == -1) {
                            e.endNode.dist = newDist;       //Burde ny distanse settes før man legger inn i kø???
                            e.endNode.ancestor = n;
                            queue.add(e.endNode);
                        }
                    }
                }
                n.visited = true;
            }

            teller++;
        }


        String res = "Antall noder tatt ut av køen: " + teller;
        Node next = end;
        res += "\nkortest distanse = " + next.dist;
        while(next !=null){
            res += "\n" + next.lat + "," + next.lon + "," + next.name + ", #FF0000";
            next = next.ancestor;
        }


        return res;
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

    class MyComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.dist - o2.dist;
        }
    }

}