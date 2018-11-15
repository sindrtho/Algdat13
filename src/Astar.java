import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Astar {
    ArrayList<Node> nodeList = new ArrayList<>();
    PriorityQueue<Node> priQue;
    int teller = 0;

    final int r = 6371; //km;

    public Astar(Grafleser leser){

        this.nodeList = (ArrayList<Node>) leser.nodes.clone();
    }

    public String run(int startNodeName, int targetNodeName){
        //Finner start- og målnode
        Node startNode = nodeList.get(startNodeName);
        Node targetNode = nodeList.get(targetNodeName);
        startNode.dist = 0;
        startNode.cost = 0;

        priQue = new PriorityQueue<>(new MyComp(targetNode));

        priQue.add(startNode);

        //Går igjennom prioritetskøen
        while(!priQue.isEmpty()){
            Node n = priQue.poll(); //Den øverste

            teller++;
            if(n != targetNode) {
                if (!n.visited) { //if it's not been visited before

                    for (Edge e : n.edgeList) { //Legge inn ny distanse hvis det er kortere enn det de er allerede

                        if (!e.endNode.visited) {

                            priQue.add(e.endNode);

                            int newDist = n.dist + e.time;
                            if (newDist < e.endNode.dist || e.endNode.dist == -1) {
                                priQue.remove(e.endNode);
                                e.endNode.dist = newDist;
                                e.endNode.ancestor = n;
                                priQue.add(e.endNode);
                            }
                        }
                    }
                    n.visited = true; //lagrer at noden har blitt besøkt
                }
            } else { //Hvis denne noden er målnoden stoppes søket og en string av pathen returneres
                String res = ""; //"N: Node, A: Ancestor, L: minLength\nStartNode: " + startNode.name + "\nN | A | L\n";
                res += "antall noder tatt ut av køen: " + teller;
                res += "\nkortest distanse = " + n.dist;
                res += "\n Breddegrader og Lengdegrader for korteste vei til kartet: ";
                while(n != null) {
                    res += "\n" + n.lat + "," + n.lon + "," + n.name + ", #FF0000";
                    n = n.ancestor;
                }
                return res;
            }
        }

        return "Den fant ikke målnoden";
    }

//    void sortPrioByCost(ArrayList<Node> liste, Node targetNode){
//
//        double b2i = targetNode.lat * Math.PI / 180;
//        double l2i = targetNode.lat * Math.PI / 180;
//
//        for(int i = 0; i < liste.size(); i++){
//            for(int j = i; j < liste.size(); j++){
//
//                //Finner distansen (cost) fra node i og j til målnoden og setter cost for nodene
//                double b1i = liste.get(i).lat * Math.PI / 180;
//                double l1i = liste.get(i).lat * Math.PI / 180;
//
//                double costi = 2 * r * Math.asin(Math.sqrt(Math.pow(Math.sin((b1i - b2i)/2),2) + (Math.cos(b1i) * Math.cos(b2i) * Math.pow(Math.sin((l1i - l2i)/2),2))));
//                liste.get(i).cost = costi;
//
//                double b1j = liste.get(j).lat * Math.PI / 180;
//                double b2j = targetNode.lat * Math.PI / 180;
//                double l1j = liste.get(j).lat * Math.PI / 180;
//                double l2j = targetNode.lat * Math.PI / 180;
//
//                double costj = 2 * r * Math.asin(Math.sqrt(Math.pow(Math.sin((b1j - b2j)/2),2) + (Math.cos(b1j) * Math.cos(b2j) * Math.pow(Math.sin((l1j - l2j)/2),2))));
//                liste.get(i).cost = costj;
//
//                //Sorterer nodene på distansen
//                if(liste.get(i).cost > liste.get(i).cost){
//                    Node temp = liste.get(i);
//                    liste.set(i, liste.get(j));
//                    liste.set(j, temp);
//                }
//            }
//        }
//    }
//
//    protected ArrayList<Node> sortNodeListByNumber(ArrayList<Node> list){
//        ArrayList<Node> sort = new ArrayList<>();
//        Node min = null;
//        while(list.size() > 0){
//            for(Node n : list){
//                if(min == null){
//                    min = n;
//                }
//                else if(Integer.parseInt(n.name) < Integer.parseInt(min.name)){
//                    min = n;
//                }
//            }
//            list.remove(min);
//            sort.add(min);
//            min = null;
//        }
//        return sort;
//    }

    class MyComp implements Comparator<Node> {

        private Node goal;

        public MyComp(Node goal) {
            this.goal = goal;
        }

        @Override
        public int compare(Node n1, Node n2) {
            int initcost1 = n1.dist;
            int initcost2 = n2.dist;
            double cost1 = 2 * r * Math.asin(
                    Math.sqrt(Math.pow(Math.sin((n1.lon - goal.lon)/2), 2)
                            + Math.cos(n1.lon) * Math.cos(goal.lon)
                            * Math.pow(Math.sin((n1.lat - goal.lat)/2), 2)));

            double cost2 = 2 * r * Math.asin(
                    Math.sqrt(Math.pow(Math.sin((n2.lon - goal.lon)/2), 2)
                            + Math.cos(n2.lon) * Math.cos(goal.lon)
                            * Math.pow(Math.sin((n2.lat - goal.lat)/2), 2)));

            return (int)Math.floor(cost1 + initcost1 + 0.5) - (int)Math.floor(cost2 + initcost2 + 0.5);
        }
    }
}
