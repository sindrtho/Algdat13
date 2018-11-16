import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Astar {
    ArrayList<Node> nodeList;
    PriorityQueue<Node> priQue;
    int teller = 0;

    static final int r = 6371; //km;

    public Astar(Grafleser leser){

        this.nodeList = leser.nodes;
    }

    public String run(int startNodeName, int targetNodeName, boolean printCoords){
        //Finner start- og målnode
        Node startNode = nodeList.get(startNodeName);
        Node targetNode = nodeList.get(targetNodeName);
        startNode.dist = 0;
        startNode.cost = 0;

        priQue = new PriorityQueue<>(new MyComp(targetNode));

        priQue.add(startNode);
        startNode.visited = true;

        Long start = System.currentTimeMillis();

        //Går igjennom prioritetskøen
        while(!priQue.isEmpty()){
            Node n = priQue.poll(); //Den øverste

            teller++;
            if(n != targetNode) {
                for (Edge e : n.edgeList) { //Legge inn ny distanse hvis det er kortere enn det de er allerede
                    if (!e.endNode.visited) {
                        int newDist = n.dist + e.time;
                        if (newDist < e.endNode.dist || e.endNode.dist == -1) {
                            e.endNode.dist = newDist;
                            e.endNode.ancestor = n;
                            e.endNode.setHaversine(targetNode);
                            priQue.add(e.endNode);
                            e.endNode.visited = true;
                        }
                    }
                }
            } else { //Hvis denne noden er målnoden stoppes søket og en string av pathen returneres

                Long end = System.currentTimeMillis();
                System.out.println("Kjøretid A-star: " + (end - start)/1000 + "sek");

                String res = ""; //"N: Node, A: Ancestor, L: minLength\nStartNode: " + startNode.name + "\nN | A | L\n";
                res += "antall noder tatt ut av køen: " + teller;
                int dist = n.dist;
                if(printCoords){
                    res += "\n Breddegrader og Lengdegrader for korteste vei til kartet: ";
                    while(n != null) {
                        res += "\n" + n.lat + "," + n.lon + "," + n.name + ", #FF0000";
                        n = n.ancestor;
                    }
                }

                Node.printTime(dist);
                return res;
            }
        }

        return "Den fant ikke målnoden";
    }

    class MyComp implements Comparator<Node> {

        private Node goal;

        public MyComp(Node goal) {
            this.goal = goal;
        }

        @Override
        public int compare(Node n1, Node n2) {
            return (int)Math.floor((n1.haversine + n1.dist) - (n2.haversine + n2.dist));
            /*
            int initcost1 = n1.dist;
            int initcost2 = n2.dist;
            double cost1 = 2 * r * Math.asin(
                    Math.sqrt(Math.pow(Math.sin((n1.lon - goal.lon)/2), 2)
                                + n1.cosLon * goal.cosLon
                                * Math.pow(Math.sin((n1.lat - goal.lat)/2), 2)));

            double cost2 = 2 * r * Math.asin(
                    Math.sqrt(Math.pow(Math.sin((n2.lon - goal.lon)/2), 2)
                            + n2.cosLon * goal.cosLon
                            * Math.pow(Math.sin((n2.lat - goal.lat)/2), 2)));

            //Lat long - bredde lengde
            double sinLats = Math.sin((n1.lat - goal.lat)/2);
            double sinLons = Math.sin((n1.lon - goal.lon)/2);
            double haversine = 2 * r * Math.asin(
                                Math.sqrt(
                                    sinLats * sinLats
                                    + n1.cosLat * goal.cosLat
                                    * sinLons * sinLons
                                ));

            return (int)Math.floor(haversine;
            //return (int)Math.floor(cost1 + initcost1 + 0.5) - (int)Math.floor(cost2 + initcost2 + 0.5);*/
        }
    }
}


/**
 Regn ut cosinus av breddegradene ved innlesing. Dette kan spare tid!
 */