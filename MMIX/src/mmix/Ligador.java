
package mmix;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ligador {
    
    private ArrayList<Integer> lengths = new ArrayList();
    private HashMap<String, String> GST = new HashMap();
    private ArrayList<HashMap<Integer, String>> useTables = new ArrayList();
    
    public ArrayList<BufferedReader> getFiles(String fileName, int charIndex){
        int i = 1;
        char done = 0;
   
        ArrayList<BufferedReader> readers = new ArrayList();
        
        while(done == 0) {
            try {
                readers.add(new BufferedReader(new InputStreamReader(new FileInputStream(fileName.replace(fileName.charAt(charIndex), (char) (i + '0'))))));  
                ++i;
            }
            catch(Exception e) {
                done = 1;
                break;
            }
        }
        return readers;
    }
    
    public void getLengths() throws IOException {
        ArrayList<BufferedReader> readers = getFiles("assembly*.txt", 8);
        String linha[];
        int length;
        
        for(BufferedReader reader: readers) {
            length = 0;
            while(true) {
                String read = reader.readLine();
                if(read == null) {
                    break;
                }
                linha = read.split(" ");
                length += linha.length;
            }
            lengths.add(length);
        }
    }
    
    public void createGST() throws IOException {
        ArrayList<BufferedReader> readers = getFiles("definition_table*.txt", 16);
        
        String linha[];
        String label;
        String address;
        int offset = 0;
        int i = 0;
        
        for(BufferedReader reader: readers) {
            while(true) {
                String read = reader.readLine();
                if(read == null) {
                    break;
                }
                linha = read.split(" ");
                label = linha[0];
                address = Integer.toString(Integer.parseInt(linha[1]) + offset);
                if(GST.containsKey(label)) {
                    System.err.println("Definição duplicada");
                    System.exit(1);
                }
                GST.put(label, address);
            }
            offset = lengths.get(i++);
        }
    }
    
    public void getUseTables() throws IOException {
        ArrayList<BufferedReader> readers = getFiles("use_table*.txt", 9);
        
        String linha[];
        HashMap useTable;
        
        for(BufferedReader reader: readers){
            useTable = new HashMap();
            while(true) {
                String read = reader.readLine();
                if(read == null) {
                    break;
                }
                linha = read.split(" ");
                useTable.put(Integer.parseInt(linha[1]), linha[0]);
            }
            useTables.add(useTable);
        }
    }
    
    public void link() throws IOException {
        ArrayList<BufferedReader> readers = getFiles("assembly*.txt", 8);
        
        getLengths();
        createGST();
        getUseTables();
        
        FileOutputStream linkedCode = new FileOutputStream("obj_code.txt");
        PrintWriter pw = new PrintWriter(linkedCode);
            
        String linha[] = new String [0];
        int i = 0;
        int LC;
        int offset = 0;
        
        for(BufferedReader reader: readers) {
            LC = 0;
            while(true) {
                String read = reader.readLine();
                if(read == null) {
                    break;
                }
                linha = read.split(" ");
                for(int j = 0; j < linha.length; ++j) {
                    if(useTables.get(i).containsKey(LC)) {
                        String label = useTables.get(i).get(LC);
                        if(!GST.containsKey(label)) {
                            System.err.println("Símbolo indefinido");
                            System.exit(1);
                        }
                        linha[j] = Integer.toString(Integer.parseInt(linha[j]) + Integer.parseInt(GST.get(label)));
                    }
                    else if(j != 0) {
                        linha[j] = Integer.toString(Integer.parseInt(linha[j]) + offset);
                    }
                    if(j > 0) {
                        pw.print(" ");
                    }
                    
                    pw.print(linha[j]);
                    ++LC;
                }
                
                pw.println();
            }
            offset = lengths.get(i++);
        }
        
        pw.close();
    }
}
