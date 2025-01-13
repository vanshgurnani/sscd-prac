import java.io.*;
// import java.util.ArrayList;
import java.util.HashMap;

public class assembler {
    public static void main(String[] args) {
        System.out.println("Hello, Assembler");

        HashMap<String, String[]> opcodeTab = new HashMap<>();
        opcodeTab.put("STOP", new String[]{"IS", "00"});
        opcodeTab.put("ADD", new String[]{"IS", "01"});
        opcodeTab.put("SUB", new String[]{"IS", "02"});
        opcodeTab.put("MULT", new String[]{"IS", "03"});
        opcodeTab.put("MOVER", new String[]{"IS", "04"});
        opcodeTab.put("MOVEM", new String[]{"IS", "05"});
        opcodeTab.put("COMP", new String[]{"IS", "06"});
        opcodeTab.put("BC", new String[]{"IS", "07"});
        opcodeTab.put("DIV", new String[]{"IS", "08"});
        opcodeTab.put("READ", new String[]{"IS", "09"});
        opcodeTab.put("PRINT", new String[]{"IS", "10"});
        opcodeTab.put("DC", new String[]{"DL", "01"});
        opcodeTab.put("DS", new String[]{"DL", "02"});
        opcodeTab.put("START", new String[]{"AD", "01"});
        opcodeTab.put("END", new String[]{"AD", "02"});

        String filepath = "input.txt";
        int locationCounter = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            System.out.println("Processing instructions from file:");

            while ((line = br.readLine()) != null){
                System.out.println(line);

                String[] tokens = line.split("\\s+");

                if (tokens.length != 2) {
                    System.out.println("Invalid line format: " + line);
                    continue;
                }

                String instruction = tokens[0];
                String value = tokens[1];

                if (instruction.equals("START")) {
                    locationCounter = Integer.parseInt(value);
                    System.out.println("LC initialized to: " + locationCounter);
                    continue;
                }


                if (opcodeTab.containsKey(instruction)) {
                    String[] details = opcodeTab.get(instruction);
                    // Print the formatted output
                    System.out.println( "LC=" + locationCounter + "(" + details[0] + ", " + details[1] + ") (C, " + value + ")");

                    locationCounter++;

                } else {
                    System.out.println("Error: Unknown instruction '" + instruction + "' in line: " + line);
                }
            }
        }

        catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
