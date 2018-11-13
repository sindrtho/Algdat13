public class Edge{
    public int time;
    public int length;
    public int speedLimit;

    public Node startNode;
    public Node endNode;
    public Edge(Node startNode, Node endNode, int time) { //, int length, int speedLimit){
        this.time = time;
//        this.length = length;
//        this.speedLimit = speedLimit;

        this.startNode = startNode;
        this.endNode = endNode;
    }

    @Override
    public String toString() {
        String res = startNode.name + " - " + endNode.name + ": " + time;// + "/" + length + "/" + speedLimit;
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
