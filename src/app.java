public class app {
    public static void main(String[] args) {
        Grafleser leser = new Grafleser("src/testfile_nodes.txt", "src/testfile_edges.txt");
        //Grafleser leser = new Grafleser("src/filer/albania-noder.txt", "src/filer/albania-kanter.txt");
        //new ListeUtskriver<Node>().utskrift("", leser.nodeList, true);
        //new ListeUtskriver<Edge>().utskrift("", leser.edgeList, true);

        System.out.println("\nDIJKSTRA:");
        String resD = new Dijkstra(leser).run(0,2);
        System.out.println(resD);

        System.out.println("\nA-STAR:");
        Astar astar = new Astar(leser);
        String resA = astar.run(0, 2);
        System.out.println(resA);
    }
}
