import java.io.*;
import java.util.*;

public class macroProcessor1 {
    static class MacroDefinition {
        String name;
        List<String> definition;

        MacroDefinition(String name) {
            this.name = name;
            this.definition = new ArrayList<>();
        }
    }
    
    private static final Map<String, MacroDefinition> MNT = new LinkedHashMap<>();
    private static final List<String> MDT = new ArrayList<>();
    private static int MNTC = 0;
    private static int MDTC = 0;

    public static void pass1(List<String> sourceCode) {
        boolean isMacroDefinition = false; // Fixed typo
        MacroDefinition currentMacro = null;

        for (String line : sourceCode) {
            String[] tokens = line.trim().split(" ");
            
            if (tokens[0].equalsIgnoreCase("MACRO")) {
                isMacroDefinition = true;
            } else if (isMacroDefinition && !tokens[0].equalsIgnoreCase("MEND")) {
                if (currentMacro == null) {
                    currentMacro = new MacroDefinition(tokens[0]);
                    MNT.put(tokens[0], currentMacro);
                    MNTC++;
                }
                currentMacro.definition.add(line);
            } else if (tokens[0].equalsIgnoreCase("MEND")) {
                if (currentMacro != null) {
                    MDT.addAll(currentMacro.definition);
                    MDT.add("MEND");
                    MDTC += currentMacro.definition.size() + 1;
                    currentMacro = null;
                    isMacroDefinition = false;
                }
            }
        }
    }

    public static List<String> readFromFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    public static void displayTables() {
        System.out.println("\nMacro Name Table (MNT) [Total: " + MNTC + "]:");
        for (String macro : MNT.keySet()) {
            System.out.println(macro);
        }

        System.out.println("\nMacro Definition Table (MDT) [Total: " + MDTC + "]:");
        for (int i = 0; i < MDT.size(); i++) {
            System.out.println((i + 1) + "\t" + MDT.get(i));
        }
    }

    public static void main(String[] args) {
        try {
            List<String> sourceCode = readFromFile("input.txt");
            pass1(sourceCode);  // Call pass1 to process macros
            displayTables();  // Display tables after processing
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }
}