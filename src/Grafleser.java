import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

public class Grafleser {
    public ArrayList<Node> nodeList = new ArrayList<>();
    public ArrayList<Edge> edgeList = new ArrayList<>();

    public Grafleser(String nodefile, String edgefile){
        /*
        try (
            RandomAccessFile aFile = new RandomAccessFile(nodefile, "r");
            FileChannel inChannel = aFile.getChannel();
        )
        {
            ArrayList<Node> nodr = new ArrayList<>();
            Long startTime = System.currentTimeMillis();

            ByteBuffer bb = ByteBuffer.allocateDirect(7);
            inChannel.read(bb);
            bb.flip();
            String count = "";
            for (int i = 0; i < bb.limit(); i++) {
                //System.out.print((char) bb.get());
                char v = (char) bb.get();
                if(v != '\n' && v != '\r'){
                    count += v;
                }
            }
            bb.clear();
            System.out.println(count);

            bb = ByteBuffer.allocateDirect(25);
            int currentNode = 0;
            while(inChannel.read(bb) > 0){
                bb.flip();
                String num = "";
                String lat = "";
                String lon = "";
                int index = 0;
                for (int i = 0; i < bb.limit(); i++)
                {
                    System.out.print((char) bb.get());
                    char v = (char) bb.get();
                    if(v == ' '){
                        //new val
                        if(i > 0){
                            index++;
                        }
                    }
                    else if(v != '\n' && v != '\r'){
                        switch (index){
                            case 0:
                                num += v;
                                break;
                            case 1:
                                lat += v;
                                break;
                            case 2:
                                lon += v;
                                break;
                        }
                    }
                    else{
                        if(!num.equals("") && !lat.equals("") && !lon.equals("")){
                            System.out.println(currentNode + ": " + num + "/" + lat + "/" + lon);
                            nodr.add(new Node(num, lat, lon));
                            currentNode++;
                            num = "";
                            lat = "";
                            lon = "";
                        }
                    }
                }
                bb.clear(); // do something with the data and clear/compact it.
            }
            System.out.println();
            System.out.println("Time-to-Nodes(0): " + ((System.currentTimeMillis() - startTime)/1000.0) + "s");
        }catch(Exception e){
            e.printStackTrace();
        }*/

        try(
            BufferedReader nodeBr = new BufferedReader(new FileReader(nodefile));
            BufferedReader edgeBr = new BufferedReader(new FileReader(edgefile));
        )
        {
            Long startTime = System.currentTimeMillis();

            String line = null;
            line = nodeBr.readLine().trim();
            int nodeCount = Integer.parseInt(line);
            line = edgeBr.readLine().trim();
            int edgeCount = Integer.parseInt(line);

            line = nodeBr.readLine();
            while (line != null){
                //System.out.println(line);
                String[] split = line.trim().split(" ");
                Node node = new Node(split[0], split[1], split[2]); //nr, kord1, kord2
                nodeList.add(node);

                line = nodeBr.readLine();
            }
            System.out.println("Time-to-Nodes: " + ((System.currentTimeMillis() - startTime)/1000.0) + "s");
            line = edgeBr.readLine();
            while (line != null){
                //System.out.println(line);
                String[] split = line.trim().split(" "); //from, to, time, length, speedlimit
                Node start = nodeList.get(nodeList.indexOf(new Node(split[0])));
                Node end = nodeList.get(nodeList.indexOf(new Node(split[1])));

                Edge edge = new Edge(start, end, Integer.parseInt(split[2]), Integer.parseInt(split[3]), Integer.parseInt(split[4]));
                edgeList.add(edge);
                start.edgeList.add(edge);

                line = edgeBr.readLine();
            }

            Long endTime = System.currentTimeMillis();
            System.out.println("Timed-to-Read: " + ((endTime - startTime)/60000.0) + "min");
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }
}
