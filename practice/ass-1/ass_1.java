import java.io.*;
import java.util.*;

public class ass_1 {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
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




        String filename = "input.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if(line.isEmpty()) continue;

                String[] tokens = line.split("\\s+");
                int tokenCount = tokens.length;

                if (tokenCount == 0) continue;

                String instruction = "";
                String operand1 = "";
                String operand2 = "";
                String label = null;

                if(opcodeTab.containsKey(tokens[0])){
                    instruction = tokens[0];
                    if(tokenCount > 1){
                        operand1 = tokens[1];
                    } 
                    if (tokenCount > 2){
                        operand2 = tokens[2];
                    }   
                }
                else {
                    label = tokens[0];
                    if(tokenCount > 1) instruction = tokens[1];
                    if(tokenCount > 2) operand1 = tokens[2];
                    if(tokenCount > 3) operand2 = tokens[3];
                }

                if("START".equals(instruction)){
                    locationCounter = operand1.isEmpty() ? 0 : Integer.parseInt(operand1);
                    continue;
                }

                if("ORIGIN".equals(instruction)){
                    locationCounter = operand1.isEmpty() ? 0 : Integer.parseInt(operand1);
                    continue;
                }

                if("END".equals(instruction)){
                    break;
                }

                if(label!= null){
                    if(!symbolTable.containsKey(label)){
                        symbolTable.put(label, locationCounter);
                    }
                }

                if(opcodeTab.containsKey(instruction)){
                    if(instruction.equals("DC")){
                        locationCounter += 1;
                    } 
                    else if(instruction.equals("DS")){
                        int size = Integer.parseInt(operand1);
                        locationCounter += size;
                    }
                    else {
                        locationCounter += 1;
                    }
                }

            }

            System.out.println("Symbol Table:");
            for(String symbol : symbolTable.keySet()){
                System.out.println(symbol + " : " + symbolTable.get(symbol));
            }

            
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
