import java.io.*;
import java.util.*;

public class ass_1 {

    public static void readFile(String filename){
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        HashMap<String, String[]> opCodeTab = new HashMap<>();
        opCodeTab.put("STOP", new String[]{"IS", "00"});
        
        System.out.println(opCodeTab);
        // readFile("input.txt");
    }
}
