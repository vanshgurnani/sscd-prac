import java.io.*;
import java.util.HashMap;

public class MachineCodeGenerator {
    public static void main(String[] args) {
        String intermediateFile = "intermediate.txt";
        String symbolTableFile = "symbol_table.txt";

        HashMap<Integer, Integer> symbolTable = new HashMap<>();

        // Load symbol table
        try (BufferedReader br = new BufferedReader(new FileReader(symbolTableFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 2) {
                    int symbolIndex = Integer.parseInt(parts[0]);
                    int address = Integer.parseInt(parts[1]);
                    symbolTable.put(symbolIndex, address);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read symbol table: " + e.getMessage());
            return;
        }

        // Process intermediate code and generate machine code
        try (BufferedReader br = new BufferedReader(new FileReader(intermediateFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                if (tokens.length < 3) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                int lc = Integer.parseInt(tokens[0]);
                String opcode = tokens[1].replaceAll("[^0-9]", "");
                String operandStr = tokens[2];
                String type = operandStr.substring(1, 2);
                int value = Integer.parseInt(operandStr.replaceAll("[^0-9]", ""));

                int operand = type.equals("S") ? symbolTable.getOrDefault(value, -1) : value;

                System.out.println(lc + " " + opcode + " " + operand);
            }
        } catch (IOException e) {
            System.err.println("Failed to process intermediate code: " + e.getMessage());
        }
    }
}
