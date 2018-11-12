public class Edge{
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
