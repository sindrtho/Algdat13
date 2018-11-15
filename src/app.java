public class
app {
    public static void main(String[] args) {
//        Grafleser leserDijkstra = new Grafleser("src/testfile_nodes.txt", "src/testfile_edges.txt");
//        Grafleser leserAstar = new Grafleser("src/testfile_nodes.txt", "src/testfile_edges.txt");
        Grafleser leserDijkstra = new Grafleser("src/filer/albania-noder.txt", "src/filer/albania-kanter.txt");
        Grafleser leserAstar = new Grafleser("src/filer/albania-noder.txt", "src/filer/albania-kanter.txt");
//        new ListeUtskriver<Node>().utskrift("", leser.nodeList, true);
//        new ListeUtskriver<Edge>().utskrift("", leser.edgeList, true);

        System.out.println("\nDIJKSTRA:");
        Long startDijkstra = System.currentTimeMillis();
        String resD = new Dijkstra(leserDijkstra).run(150,2105);
        Long endDijkstra = System.currentTimeMillis();
        System.out.println(resD);
        System.out.println("Dijkstra: " + (endDijkstra - startDijkstra)/1000 + "Sec");

        System.out.println("\nA-STAR:");
        Long startAstar = System.currentTimeMillis();
        Astar astar = new Astar(leserAstar);
        String resA = astar.run(150, 2105);
        Long endAstar = System.currentTimeMillis();
        System.out.println(resA);
        System.out.println("Astar: " + (endAstar - startAstar)/1000 + "Sec");
    }
}
