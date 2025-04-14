import java.util.*;
import java.io.*;

public class ass2 {
    public static void main(String[] args) {
        String interCode = "intermediate.txt";
        String symbolTableFile = "symbol_table.txt";

        // Symbol Index -> Address
        HashMap<Integer, Integer> symbolTable = new HashMap<>();

        // Read symbol table
        try (BufferedReader br = new BufferedReader(new FileReader(symbolTableFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                if (tokens.length == 2) {
                    int symbolIndex = Integer.parseInt(tokens[0]);
                    int address = Integer.parseInt(tokens[1]);
                    symbolTable.put(symbolIndex, address);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        // Read intermediate code & generate machine code
        try (BufferedReader br = new BufferedReader(new FileReader(interCode))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");

                if (tokens.length < 3) continue;

                String location = tokens[0];

                String opcodePart = tokens[1]; // e.g., (IS, 04)
                String operandPart = tokens[2]; // e.g., (S, 1) or (C, 10)

                // Extract opcode
                String opcode = opcodePart.substring(opcodePart.indexOf(",") + 1, opcodePart.indexOf(")"));

                // Extract operand type and value
                String operandType = operandPart.substring(1, operandPart.indexOf(","));
                int operandVal = Integer.parseInt(operandPart.substring(operandPart.indexOf(",") + 1, operandPart.indexOf(")")));

                // Resolve operand address
                int finalOperand = 0;
                if (operandType.equals("S")) {
                    finalOperand = symbolTable.getOrDefault(operandVal, 0);
                } else if (operandType.equals("C")) {
                    finalOperand = operandVal;
                }

                // Print final machine code
                System.out.println(location + " " + opcode + " 00 " + finalOperand);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
