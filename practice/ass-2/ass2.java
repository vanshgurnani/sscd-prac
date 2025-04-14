import java.io.*;
import java.util.HashMap;

public class ass2 {
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
                
                int lc = Integer.parseInt(tokens[0]);
                String opcode = tokens[2].replace(")", "");
                String operand = tokens[3].replace(",", "").replace("(", "");
                String operandValue = tokens[4].replace(")", "");

                int finalOperand = 0;
                if (operand.equals("S")) {
                    finalOperand = symbolTable.get(Integer.parseInt(operandValue));
                } else if(operand.equals("C")){
                    finalOperand = Integer.parseInt(operandValue);
                }


                System.out.println(lc + " " + opcode + " " + "00" + " " + finalOperand);
            }
        } catch (IOException e) {
            System.err.println("Failed to process intermediate code: " + e.getMessage());
        }
    }
}
