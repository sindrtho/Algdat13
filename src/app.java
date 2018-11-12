public class app {
    public static void main(String[] args) {
        Grafleser leser = new Grafleser("testfile_nodes", "testfile_edges");
        Dijkstra dijkstra = new Dijkstra(leser);
        dijkstra.run(0);
        //A* here
    }
}
