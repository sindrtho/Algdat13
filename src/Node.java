import java.util.ArrayList;
public class Node implements Comparable<Node>{
    public String name;
    public double lat;
    public double lon;
    final int MAX_INTEGER = Integer.MAX_VALUE;

    public ArrayList<Edge> edgeList = new ArrayList<>();
    public Node ancestor = null;
    public int dist = MAX_INTEGER;
    public boolean visited = false;
    public double cost;

    public double radLat;
    public double radLon;

    public double haversine;

    public Node(String name){
        this.name = name;
    }
    public Node(String name, String lat, String lon){
        this.name = name;
        this.lat = Double.parseDouble(lat);
        this.lon = Double.parseDouble(lon);
        radLat = 180/Math.PI * this.lat;
        radLon = 180/Math.PI * this.lon;
    }

    public double setHaversine(Node goal){
        double cosLat = Math.cos(this.radLat);
        double sinLats = Math.sin((radLat - goal.radLat)/2);
        double sinLons = Math.sin((radLon - goal.radLon)/2);
        haversine = 2 * Astar.r *
                Math.asin(
                    Math.sqrt(
                        sinLats * sinLats
                        + cosLat * Math.cos(goal.radLat)
                        * sinLons * sinLons
                    )
                );
        return haversine;
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

    public static void printTime(int centiSec) {
        String base = "Korteste rute tar: ";
        int timer = (int)(((double)centiSec)/360000);
        int min = (int)((((double)centiSec)%360000)/6000);
        int sec = (int)(((((double)centiSec)%360000)%6000)/100);
        System.out.println(base + timer + " timer, " + min + " minutter, og " + sec + " sekunder.");
    }
}
