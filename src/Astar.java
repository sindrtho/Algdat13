import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Astar {
    ArrayList<Node> nodeList = new ArrayList<>();
    ArrayList<Edge> edgelist = new ArrayList<>();
    ArrayList<Node> prio = new ArrayList<>();
    PriorityQueue<Node> priQue;
    int teller = 0;

    final int r = 6371; //km;

    public Astar(Grafleser leser){

        this.nodeList = (ArrayList<Node>) leser.nodes.clone();
//        this.nodeList = (ArrayList<Node>) leser.nodeList.clone();
    }

    public String run(int startNodeName, int targetNodeName){
        //Finner start- og målnode
        Node startNode = nodeList.get(startNodeName);
        Node targetNode = nodeList.get(targetNodeName);
        startNode.dist = 0;
        startNode.cost = 0;

        priQue = new PriorityQueue<>(new MyComp(targetNode));

        //Legger startnode inn i køen
        //prio.add(startNode);
        priQue.add(startNode);
        //Finner distansen fra start- til målnoden
//        double b1 = startNode.lat * Math.PI / 180;
//        double b2 = targetNode.lat * Math.PI / 180;
//        double l1 = startNode.lat * Math.PI / 180;
//        double l2 = targetNode.lat * Math.PI / 180;
//
//        double cost = 2 * r * Math.asin(Math.sqrt(Math.pow(Math.sin((b1 - b2)/2),2) + (Math.cos(b1) * Math.cos(b2) * Math.pow(Math.sin((l1 - l2)/2),2))));

        //Går igjennom prioritetskøen
        while(!priQue.isEmpty()){
//            sortPrioByCost(prio, targetNode);
            Node n = priQue.poll(); //Den øverste

            if(n != targetNode) {
                if (!n.visited) { //if it's not been visited before

                    for (Edge e : n.edgeList) { //Legge inn ny distanse hvis det er kortere enn det de er allerede

                        if (!e.endNode.visited) {
//                            if (!prio.contains(e.endNode)) {
//                                prio.add(e.endNode);
//                            }
                            int newDist = n.dist + e.time;
                            if (newDist < e.endNode.dist || e.endNode.dist == -1) {
//                                priQue.add(e.endNode);
                                e.endNode.dist = newDist;
                                e.endNode.ancestor = n;
//                                if (priQue.contains(e.endNode))
//                                    priQue.remove(e.endNode);
                                priQue.add(e.endNode);
                            }
                        }
                    }
                    n.visited = true; //lagrer at noden har blitt besøkt
                }
//            prio.remove(n); //Er nå ferdig med noden og den tas ut av køen
                teller++;
            } else { //Hvis denne noden er målnoden stoppes søket og en string av pathen returneres
//                nodeList = sortNodeListByNumber(nodeList);
                String res = ""; //"N: Node, A: Ancestor, L: minLength\nStartNode: " + startNode.name + "\nN | A | L\n";
//                for(Node no : nodeList){
//                    res += no.name + " | " +  (no.ancestor != null ? no.ancestor.name : "§") + " | " + no.dist + "\n";
//                }
                res += "antall noder tatt ut av køen: " + teller;
                res += "\nkortest distanse = " + n.dist;
                res += "\n Breddegrader og Lengdegrader for korteste vei til kartet: ";
//                for(Node no : nodeList){
//                    res += "\n" + no.lat + "," + no.lon + "," + no.name + ", #FF0000";
//                    if(no == targetNode)
//                        break;
//              }
                while(n != null) {
                    res += "\n" + n.lat + "," + n.lon + "," + n.name + ", #FF0000";
                    n = n.ancestor;
                }
                return res;
            }
        }

        return "Den fant ikke målnoden";
    }

    void sortPrioByCost(ArrayList<Node> liste, Node targetNode){

        double b2i = targetNode.lat * Math.PI / 180;
        double l2i = targetNode.lat * Math.PI / 180;

        for(int i = 0; i < liste.size(); i++){
            for(int j = i; j < liste.size(); j++){

                //Finner distansen (cost) fra node i og j til målnoden og setter cost for nodene
                double b1i = liste.get(i).lat * Math.PI / 180;
                double l1i = liste.get(i).lat * Math.PI / 180;

                double costi = 2 * r * Math.asin(Math.sqrt(Math.pow(Math.sin((b1i - b2i)/2),2) + (Math.cos(b1i) * Math.cos(b2i) * Math.pow(Math.sin((l1i - l2i)/2),2))));
                liste.get(i).cost = costi;

                double b1j = liste.get(j).lat * Math.PI / 180;
                double b2j = targetNode.lat * Math.PI / 180;
                double l1j = liste.get(j).lat * Math.PI / 180;
                double l2j = targetNode.lat * Math.PI / 180;

                double costj = 2 * r * Math.asin(Math.sqrt(Math.pow(Math.sin((b1j - b2j)/2),2) + (Math.cos(b1j) * Math.cos(b2j) * Math.pow(Math.sin((l1j - l2j)/2),2))));
                liste.get(i).cost = costj;

                //Sorterer nodene på distansen
                if(liste.get(i).cost > liste.get(i).cost){
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
                    Math.sqrt(Math.pow(Math.sin((n1.lat - goal.lat)/2), 2)
                            + Math.cos(n1.lat) * Math.cos(goal.lat)
                            * Math.pow(Math.sin((n1.lon - goal.lon)/2), 2)));

            double cost2 = 2 * r * Math.asin(
                    Math.sqrt(Math.pow(Math.sin((n2.lat - goal.lat)/2), 2)
                            + Math.cos(n2.lat) * Math.cos(goal.lat)
                            * Math.pow(Math.sin((n2.lon - goal.lon)/2), 2)));

            return initcost1 + cost1 < initcost2 + cost2 ? 1 : initcost1 + cost1 > initcost2 + cost2 ? -1 : 0;
        }
    }
}
