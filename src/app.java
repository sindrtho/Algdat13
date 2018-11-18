public class
app {
    public static void main(String[] args) {
        int trondheim = 2822045;
        int bergen = 3380290;

        int oslo = 2806952;
        int stockholm = 4510542;
        int helsinki = 1233277;
//        Grafleser leserDijkstra = new Grafleser("src/testfile_nodes.txt", "src/testfile_edges.txt");
//        Grafleser leserAstar = new Grafleser("src/testfile_nodes.txt", "src/testfile_edges.txt");

        //Grafleser leserDijkstra = new Grafleser(false, "src/filer/albania-noder.txt", "src/filer/albania-kanter.txt");
        //Grafleser leserAstar = new Grafleser(false, "src/filer/albania-noder.txt", "src/filer/albania-kanter.txt");
        //Grafleser leserDijkstra = new Grafleser(true, "http://www.iie.ntnu.no/fag/_alg/Astjerne/opg/noder.txt", "http://www.iie.ntnu.no/fag/_alg/Astjerne/opg/kanter.txt");
        //Grafleser leserAstar = new Grafleser(true, "http://www.iie.ntnu.no/fag/_alg/Astjerne/opg/noder.txt", "http://www.iie.ntnu.no/fag/_alg/Astjerne/opg/kanter.txt");
//        Grafleser leserDijkstra = new Grafleser("src/filer/noder.txt", "src/filer/kanter.txt");
//        Grafleser leserAstar = new Grafleser("src/filer/noder.txt", "src/filer/kanter.txt");
//        new ListeUtskriver<Node>().utskrift("", leser.nodeList, true);
//        new ListeUtskriver<Edge>().utskrift("", leser.edgeList, true);


        Grafleser leserDijkstra = new Grafleser(false, "src/filer/albania-noder.txt", "src/filer/albania-kanter.txt");
        System.out.println("\nDIJKSTRA:");
        String resD = new Dijkstra(leserDijkstra).run(0, 50000, false);
        System.out.println(resD);

        System.out.println("");

        Grafleser leserAstar = new Grafleser(false, "src/filer/albania-noder.txt", "src/filer/albania-kanter.txt");
        System.out.println("\nA-STAR:");
        Astar astar = new Astar(leserAstar);
        String resA = astar.run(0, 50000, false);
        System.out.println(resA);
    }
}
