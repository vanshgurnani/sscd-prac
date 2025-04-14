import java.io.*;
import java.util.*;

public class ass {
    public static void main(String[] args) {
        System.out.println("Hello World!");
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
        opcodeTab.put("ORIGIN", new String[]{"AD", "03"});


        HashMap<String, String> registerTab = new HashMap<>();
        registerTab.put("AREG", "1");
        registerTab.put("BREG", "2");
        registerTab.put("CREG", "3");
        registerTab.put("DREG", "4");


        HashMap<String, Integer> symbolTable = new HashMap<>();
        int locationCounter = 0;
        List<String[]> intermediateCode = new ArrayList<>();







        String filename = "input.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if (line.isEmpty()) {
                    continue;
                }

                String[] tokens = line.trim().split("\\s+");
                if (tokens.length == 0) continue;

                String instruction = "";
                String operand1 = "";
                String operand2 = "";
                String label = null;

                if (opcodeTab.containsKey(tokens[0])) {
                    instruction = tokens[0];
                    if (tokens.length > 1) operand1 = tokens[1];
                    if (tokens.length > 2) operand2 = tokens[2];
                } else {
                    label = tokens[0];
                    instruction = tokens[1];
                    if (tokens.length > 2) operand1 = tokens[1];
                    if (tokens.length > 3) operand2 = tokens[2];
                }

                if ("START".equals(instruction)){
                    locationCounter = operand1.isEmpty() ? 0 : Integer.parseInt(operand1);
                    continue;
                }

                if ("ORIGIN".equals(instruction)){
                    locationCounter = operand1.isEmpty() ? 0 : Integer.parseInt(operand1);
                    continue;
                }

                if ("END".equals(instruction)){
                    break;
                }

                if (label != null) {
                    if (!symbolTable.containsKey(label)) {
                        symbolTable.put(label, locationCounter);
                    }
                }

                if (opcodeTab.containsKey(instruction)) {
                    intermediateCode.add(new String[]{String.valueOf(locationCounter), instruction, operand1, operand2});
                    if (instruction.equals("DS")) {
                        int size = Integer.parseInt(operand1);
                        locationCounter += size;
                    }
                    locationCounter ++;
                }

            }

        } catch (Exception e) {
            System.err.println(e);
        }

        System.out.println("Symbol Table:");
        for (Map.Entry<String, Integer> entry : symbolTable.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("intermediateCode:");
        for (String[] entry : intermediateCode){
            String loc = entry[0];
            String instr = entry[1];
            String op1 = entry[2];
            String op2 = entry[3];

            String[] opcodeInfo = opcodeTab.get(instr);
            String opcode = opcodeInfo[0];
            String code = opcodeInfo[1];

            String regCode = registerTab.get(op1);
            if (regCode == null) {
                regCode = "0"; // Default value if not found in register table
            }

            System.out.println(loc + " " + opcode + " " + code + " " + regCode + " " + op2);
        }

    }
}
