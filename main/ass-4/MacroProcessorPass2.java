import java.io.*;
import java.util.*;

public class MacroProcessorPass2 {
    private static final Map<String, Integer> MNT = new LinkedHashMap<>();
    private static final List<String> MDT = new ArrayList<>();
    private static final List<String> ALA = new ArrayList<>();
    private static final Map<String, String> ALP = new LinkedHashMap<>();

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

    public static void expandMacroCalls() {
        for (Map.Entry<String, String> entry : ALP.entrySet()) {
            String macroName = entry.getKey();
            String actualArg = entry.getValue();

            if (MNT.containsKey(macroName)) {
                int index = MNT.get(macroName) - 1;

                System.out.println("\nExpanding Macro: " + macroName);

                while (index < MDT.size()) {
                    String line = MDT.get(index);
                    if (line.equalsIgnoreCase("MEND")) break;

                    for (String formalArg : ALA) {
                        line = line.replace(formalArg, actualArg);
                    }

                    System.out.println(line);
                    index++;
                }
            } else {
                System.out.println("Error: Macro " + macroName + " not found in MNT.");
            }
        }
    }

    public static void main(String[] args) {
        try {
            loadTables("mnt.txt", "mdt.txt", "ala.txt", "alp.txt");

            System.out.println("\n--- Macro Expansion Output ---");
            expandMacroCalls();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
