import java.io.*;
import java.util.HashMap;

public class assembler {
    public static void main(String[] args) {
        System.out.println("Hello, Assembler");

        // Opcode Table
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
        opcodeTab.put("ORIGIN", new String[]{"AD", "03"});  // Added support for ORIGIN

        // Register Table
        HashMap<String, String> registerTab = new HashMap<>();
        registerTab.put("AREG", "1");
        registerTab.put("BREG", "2");
        registerTab.put("CREG", "3");
        registerTab.put("DREG", "4");

        String filepath = "input.txt";
        int locationCounter = 0;

        // Symbol Table
        HashMap<String, Integer> symbolTable = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            System.out.println("Processing instructions from file:");

            while ((line = br.readLine()) != null) {
                // Trim and ignore empty lines
                line = line.trim();
                if (line.isEmpty()) continue;

                // Split the line into tokens
                String[] tokens = line.split("\\s+");
                int tokenCount = tokens.length;

                if (tokenCount == 0) continue;

                String instruction = "";
                String operand1 = "";
                String operand2 = "";
                String label = null;

                // If the first token is an instruction
                if (opcodeTab.containsKey(tokens[0])) {
                    instruction = tokens[0];
                    if (tokenCount > 1) operand1 = tokens[1];
                    if (tokenCount > 2) operand2 = tokens[2];
                } 
                // If the first token is NOT an instruction, assume it's a label
                else {
                    label = tokens[0];
                    if (tokenCount > 1) instruction = tokens[1];
                    if (tokenCount > 2) operand1 = tokens[2];
                    if (tokenCount > 3) operand2 = tokens[3];
                }

                // Handle START directive
                if ("START".equals(instruction)) {
                    locationCounter = operand1.isEmpty() ? 0 : Integer.parseInt(operand1);
                    System.out.println("LC initialized to: " + locationCounter);
                    continue;
                }

                // Handle ORIGIN directive (change location counter)
                if ("ORIGIN".equals(instruction)) {
                    locationCounter = Integer.parseInt(operand1);
                    System.out.println("LC updated to: " + locationCounter + " due to ORIGIN");
                    continue;
                }

                // If the instruction is END, stop processing
                if ("END".equals(instruction)) {
                    System.out.println("END reached. Final LC: " + locationCounter);
                    break;
                }

                // If a label is encountered, add it to the symbol table
                if (label != null) {
                    if (!symbolTable.containsKey(label)) {
                        symbolTable.put(label, locationCounter);
                    }
                    // After processing the label, treat it like an instruction
                    instruction = tokens[1];  // Next token is the instruction
                    if (tokenCount > 2) operand1 = tokens[2];
                    if (tokenCount > 3) operand2 = tokens[3];
                }

                // If instruction is valid
                if (opcodeTab.containsKey(instruction)) {
                    String[] details = opcodeTab.get(instruction);

                    // If operands contain registers, replace them with corresponding values
                    if (registerTab.containsKey(operand1)) {
                        operand1 = registerTab.get(operand1);
                    }

                    // Handle DL type instructions (DC, DS)
                    if (instruction.equals("DC")) {
                        System.out.println("LC=" + locationCounter + " (" + details[0] + ", " + details[1] + ") (C, " + operand1 + ")");
                        locationCounter++;
                    } else if (instruction.equals("DS")) {
                        int size = Integer.parseInt(operand1);
                        System.out.println("LC=" + locationCounter + " (" + details[0] + ", " + details[1] + ") (C, " + operand1 + ") -> Allocating " + size + " memory units");
                        locationCounter += size;
                    } else {
                        System.out.println("LC=" + locationCounter + " (" + details[0] + ", " + details[1] + ") " + 
                        (!operand1.isEmpty() ? "(C, " + operand1 + ")" : "") + " " +
                        (!operand2.isEmpty() ? "(C, " + operand2 + ")" : ""));
                        locationCounter++;
                    }
                } else {
                    System.out.println("Error: Unknown instruction '" + instruction + "' in line: " + line);
                }
            }

            // Print the symbol table
            System.out.println("\nSymbol Table:");
            for (String symbol : symbolTable.keySet()) {
                System.out.println(symbol + " => " + symbolTable.get(symbol));
            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
