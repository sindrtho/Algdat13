import java.util.ArrayList;

public class ListeUtskriver<T> {
    public void utskrift(String msg, ArrayList<T> liste, boolean newLine){
        String res = msg;
        if(newLine){
            for(int i = 0; i < liste.size(); i++){
                res += liste.get(i).toString() + "\n";
            }
            System.out.println(res);
        }
        else{
            for(int i = 0; i < liste.size(); i++){
                res += liste.get(i).toString() + ", ";
            }
            System.out.println(res + "\n");
        }
    }
}

