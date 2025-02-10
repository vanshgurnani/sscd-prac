import java.io.*;
import java.util.*;

public class MacroProcessorPass2 {
    private static final Map<String, Integer> MNT = new LinkedHashMap<>(); // Macro Name Table
    private static final List<String> MDT = new ArrayList<>(); // Macro Definition Table
    private static final List<String> ALA = new ArrayList<>(); // Argument List Array
    private static final Map<String, String> ALP = new LinkedHashMap<>(); // Actual Argument List

    public static void loadTables(String mntFile, String mdtFile, String alaFile, String alpFile) throws IOException {
        // Load MNT
        try (BufferedReader br = new BufferedReader(new FileReader(mntFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                if (tokens.length == 2) {
                    MNT.put(tokens[0], Integer.parseInt(tokens[1]));
                }
            }
        }

        // Load MDT
        try (BufferedReader br = new BufferedReader(new FileReader(mdtFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                MDT.add(line);
            }
        }

        // Load ALA
        try (BufferedReader br = new BufferedReader(new FileReader(alaFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                ALA.add(line.trim());
            }
        }

        // Load ALP
        try (BufferedReader br = new BufferedReader(new FileReader(alpFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                if (tokens.length == 2) {
                    ALP.put(tokens[0], tokens[1]);
                }
            }
        }
    }

    public static List<String> expandMacroCalls() {
        List<String> expandedCode = new ArrayList<>();

        for (Map.Entry<String, String> entry : ALP.entrySet()) {
            String macroName = entry.getKey();
            String actualArg = entry.getValue();

            if (MNT.containsKey(macroName)) {
                int index = MNT.get(macroName) - 1; // MDT index (0-based)

                System.out.println("\nExpanding Macro: " + macroName + " (MDT Index: " + (index + 1) + ")");
                
                if (index >= MDT.size()) {
                    System.out.println("Error: MDT Index out of bounds! Index=" + index + ", MDT Size=" + MDT.size());
                    continue;
                }

                while (index < MDT.size()) {
                    String expandedLine = MDT.get(index);

                    // Stop expansion when MEND is reached
                    if (expandedLine.equalsIgnoreCase("MEND")) {
                        break;
                    }

                    // Replace formal argument with actual argument
                    for (String formalArg : ALA) {
                        expandedLine = expandedLine.replace(formalArg, actualArg);
                    }

                    expandedCode.add(expandedLine);
                    System.out.println("Generated: " + expandedLine);

                    index++;
                }
            } else {
                System.out.println("Error: Macro " + macroName + " not found in MNT.");
            }
        }

        return expandedCode;
    }

    public static void writeToFile(String filename, List<String> lines) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Load macro tables from files
            loadTables("mnt.txt", "mdt.txt", "ala.txt", "alp.txt");

            // Perform Pass 2 (macro expansion)
            List<String> expandedCode = expandMacroCalls();

            // Display expanded code
            System.out.println("\nExpanded Macro Code:");
            for (String line : expandedCode) {
                System.out.println(line);
            }

            // Write expanded code to output file
            writeToFile("expanded_code.txt", expandedCode);
            System.out.println("\nExpanded code saved in 'expanded_code.txt'");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
