import java.util.ArrayList;
public class Node {
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
