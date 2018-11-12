import java.util.ArrayList;
public class Node implements Comparable<Node>{
    public String name;
    public double lat;
    public double lon;

    public ArrayList<Edge> edgeList = new ArrayList<>();
    public Node ancestor = null;
    public int dist = -1;
    public boolean visited = false;
    public double cost;

    public Node(String name){
        this.name = name;
    }
    public Node(String name, String lat, String lon){
        this.name = name;
        this.lat = Double.parseDouble(lat);
        this.lon = Double.parseDouble(lon);
    }

    @Override
    public String toString() {
        String res = name + "(" + lat + "/" + lon + ")(" + dist + "); ";
        for(Edge e : edgeList){
            res += e.toString() + " | ";
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

    @Override
    public int compareTo(Node node) {
        if(node == this || node.name.equals(this.name)){
            return 0;
        }
        else if(node.dist < this.dist){
            return 1;
        }
        return 0;
    }
}
