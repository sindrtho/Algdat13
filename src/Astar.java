import java.util.ArrayList;
import java.util.PriorityQueue;

public class Astar {
    ArrayList<Node> nodeList = new ArrayList<>();
    ArrayList<Edge> edgelist = new ArrayList<>();
    ArrayList<Node> prio = new ArrayList<>();
    int teller = 0;

    final int r = 6371; //km;

    public Astar(Grafleser leser){

        this.nodeList = (ArrayList<Node>) leser.nodeList.clone();
    }

    public String run(int startNodeName, int targetNodeName){
        //Finner start- og målnode
        Node startNode = nodeList.get(startNodeName);
        Node targetNode = nodeList.get(targetNodeName);
        startNode.dist = 0;

        //Legger startnode inn i køen
        prio.add(startNode);

        //Finner distansen fra start- til målnoden
//        double b1 = startNode.lat * Math.PI / 180;
//        double b2 = targetNode.lat * Math.PI / 180;
//        double l1 = startNode.lat * Math.PI / 180;
//        double l2 = targetNode.lat * Math.PI / 180;
//
//        double cost = 2 * r * Math.asin(Math.sqrt(Math.pow(Math.sin((b1 - b2)/2),2) + (Math.cos(b1) * Math.cos(b2) * Math.pow(Math.sin((l1 - l2)/2),2))));

        //Går igjennom prioritetskøen
        while(prio.size() > 0){
            sortPrioByCost(prio, targetNode);
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
                n.visited = true; //lagrer at noden har blitt besøkt
            }
            prio.remove(n); //Er nå ferdig med noden og den tas ut av køen
            teller++;

            if(n == targetNode){ //Hvis denne noden er målnoden stoppes søket og en string av pathen returneres
                nodeList = sortNodeListByNumber(nodeList);
                String res = "N: Node, A: Ancestor, L: minLength\nStartNode: " + startNode.name + "\nN | A | L\n";
                for(Node no : nodeList){
                    res += no.name + " | " +  (no.ancestor != null ? no.ancestor.name : "§") + " | " + no.dist + "\n";
                }
                res += "antall noder tatt ut av køen: " + teller;
                return res + "\nkortest distanse = " + n.dist;
            }
        }

        return "Den fant ikke målnoden";
    }

    void sortPrioByCost(ArrayList<Node> liste, Node targetNode){

        for(int i = 0; i < liste.size(); i++){
            for(int j = i; j < liste.size(); j++){

                //Finner distansen (cost) fra node i og j til målnoden og setter cost for nodene
                double b1i = liste.get(i).lat * Math.PI / 180;
                double b2i = targetNode.lat * Math.PI / 180;
                double l1i = liste.get(i).lat * Math.PI / 180;
                double l2i = targetNode.lat * Math.PI / 180;

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

}
