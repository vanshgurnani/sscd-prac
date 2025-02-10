import java.io.*;
import java.util.HashMap;

public class MachineCodeGenerator {
    public static void main(String[] args) {
        String intermediateFile = "intermediate.txt";
        String symbolTableFile = "symbol_table.txt";
        String outputFile = "machine_code.txt";

        HashMap<Integer, Integer> symbolTable = new HashMap<>();

        // Step 1: Read Symbol Table and Store in HashMap
        try (BufferedReader br = new BufferedReader(new FileReader(symbolTableFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                if (tokens.length == 2) {
                    symbolTable.put(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading symbol table: " + e.getMessage());
            return;
        }

        // Step 2: Process Intermediate Code and Generate Machine Code
        try (BufferedReader br = new BufferedReader(new FileReader(intermediateFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                if (tokens.length < 3) {
                    System.out.println("Invalid format: " + line);
                    continue;
                }

                int LC = Integer.parseInt(tokens[0]); // Location Counter
                String opcode = tokens[1].replaceAll("[^0-9]", ""); // Extract opcode

                String operandType = tokens[2].substring(1, 2); // Extract type (S = Symbol, C = Constant)
                String operandValue = tokens[2].replaceAll("[^0-9]", ""); // Extract operand value

                int operand;
                if (operandType.equals("S")) {
                    operand = symbolTable.getOrDefault(Integer.parseInt(operandValue), -1);
                } else {
                    operand = Integer.parseInt(operandValue);
                }

                // Write to output file
                String machineCode = LC + " " + opcode + " " + operand;
                bw.write(machineCode);
                bw.newLine();
                System.out.println(machineCode);
            }

            System.out.println("Machine code generated in " + outputFile);

        } catch (IOException e) {
            System.err.println("Error processing intermediate code: " + e.getMessage());
        }
    }
}
