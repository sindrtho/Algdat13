public class app {
    public static void main(String[] args) {
        //Grafleser leser = new Grafleser("src/testfile_nodes.txt", "src/testfile_edges.txt");
        Grafleser leser = new Grafleser("src/filer/albania-noder.txt", "src/filer/albania-kanter.txt");
        //new ListeUtskriver<Node>().utskrift("", leser.nodeList, true);
        //new ListeUtskriver<Edge>().utskrift("", leser.edgeList, true);
        
        //int time = new Dijkstra(leser).run(0,1);
        //System.out.println("Minste tid fra 1 -> 2: " + (time >= 0 ? time : "Ingen vei"));

        //A* here
        Astar astar = new Astar(leser);
        System.out.println(astar.run(0, 2));

    }
}
