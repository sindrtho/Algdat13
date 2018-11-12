public class app {
    public static void main(String[] args) {
        Grafleser leser = new Grafleser("src/testfile_nodes.txt", "src/testfile_edges.txt");
        new ListeUtskriver<Node>().utskrift("", leser.nodeList, true);
        new ListeUtskriver<Edge>().utskrift("", leser.edgeList, true);

        //Dijkstra dijkstra = new Dijkstra(leser);
        //dijkstra.run(0);

        //A* here
        Astar astar = new Astar(leser);
        System.out.println(astar.run(0, 2));

    }
}
