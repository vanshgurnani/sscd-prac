import java.util.*;
import java.io.*;

public class ass2 {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        String interCode = "intermediate.txt";
        String symbolTableFile = "symbol_table.txt";

        HashMap<Integer, Integer> symbolTable = new HashMap<>();

        try(BufferedReader br = new BufferedReader(new FileReader(symbolTableFile))){
            String line;
            while((line = br.readLine()) != null){
                // System.out.println(line);
                String[] tokens = line.trim().split("\\s+");
                if(tokens.length == 2){
                    int symbolIndex = Integer.parseInt(tokens[0]);
                    int address = Integer.parseInt(tokens[1]);
                    symbolTable.put(symbolIndex, address);
                }
            }

        } catch (IOException e){
            System.out.println(e);
        }


        try(BufferedReader br = new BufferedReader(new FileReader(interCode))){
            String line;
            while((line = br.readLine()) != null){
                String[] tokens = line.trim().split("\\s+");
                // System.out.println(tokens.length);

                int lc = Integer.parseInt(tokens[0]);
                String opcode = tokens[1].replaceAll("[^0-9]", "");
                String operandStr = tokens[2];
                String type = operandStr.substring(1, 2);
                int value = Integer.parseInt(operandStr.replaceAll("[^0-9]", ""));

                int operand = type.equals("S") ? symbolTable.getOrDefault(value, -1) : value;

                System.out.println("lc: " + lc + " opcode: " + opcode + " operandStr: " + operandStr + " type: " + type + " value: " + value + " operand: " + operand);

                // String opcodePart = tokens[1];
            }

        } catch (IOException e){
            System.out.println(e);
        }
    }
}
