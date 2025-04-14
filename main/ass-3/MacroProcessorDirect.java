import java.util.*;
import java.io.*;

public class MacroProcessorDirect {

    // Macro Tables
    private static final Map<String, Integer> MNT = new LinkedHashMap<>();
    private static final List<String> MDT = new ArrayList<>();
    private static int MDTC = 0;

    public static void readFile(String filename) {
        boolean isMacroDefinition = false;
        String currentMacroName = null;
        int currentMDTIndex = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println("Reading: " + line);
                String[] tokens = line.trim().split("\\s+");

                if (tokens.length == 0 || tokens[0].isEmpty()) continue;

                if (tokens[0].equalsIgnoreCase("MACRO")) {
                    isMacroDefinition = true;
                } else if (isMacroDefinition) {
                    if (tokens[0].equalsIgnoreCase("MEND")) {
                        MDT.add("MEND");
                        MDTC++;
                        isMacroDefinition = false;
                        currentMacroName = null;
                    } else {
                        if (currentMacroName == null) {
                            currentMacroName = tokens[0];
                            currentMDTIndex = MDTC + 1;
                            MNT.put(currentMacroName, currentMDTIndex);
                        }
                        MDT.add(line);
                        MDTC++;
                    }
                }
                
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        displayTables();
    }

    public static void displayTables() {
        System.out.println("\n=== Macro Name Table (MNT) ===");
        System.out.println("Index\tMacro Name\tMDT Index");
        int index = 1;
        for (Map.Entry<String, Integer> entry : MNT.entrySet()) {
            System.out.println(index + "\t" + entry.getKey() + "\t\t" + entry.getValue());
            index++;
        }

        System.out.println("\n=== Macro Definition Table (MDT) ===");
        for (int i = 0; i < MDT.size(); i++) {
            System.out.println((i + 1) + "\t" + MDT.get(i));
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Pass 1: Macro Processor (Direct Version) ===");
        readFile("input.txt");
    }
}
