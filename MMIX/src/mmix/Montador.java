//ESTOU CONSIDERANDO QUE OS LABELS NÃO PODEM CONTER "$"

package trabalhops;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class Montador {
    
    HashMap montadorMap = new HashMap();
    HashMap operandosMap = new HashMap();

    public void getInstructions() {
        
        String linha;
        String palavras[] = new String[2];
        int i = 0;
        try {
            
            FileInputStream arquivo = new FileInputStream("instrucoes.txt");
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader instrucoes = new BufferedReader(input);
            
            while (i < 4) {

                linha = instrucoes.readLine();
                palavras = linha.split(" ");
                
                montadorMap.put(palavras[0], palavras[1]);
           
                i++;
            } 
        }
        catch (Exception e) {
                
            System.out.println("Erro ao abrir o arquivo das instruções");
        } 
    }
    
    public void getCode() {
        
        String linha;
        String operandos[];
        String label[];
        String aux[];
        int count_add = 0, flag_operandos = 0, j = 0, k = 0;
        
        try {
            
            FileInputStream arquivo = new FileInputStream("codigo.txt");
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader instrucoes = new BufferedReader(input);
            
            FileOutputStream arquivo2 = new FileOutputStream("symbol_table.txt");
            PrintWriter pw = new PrintWriter(arquivo2);
            
            pw.println("Símbolo | Endereço");
            
            while ((linha = instrucoes.readLine()) != null) {
                
               // System.out.println(linha);
                //VERIFICA SE A INSTRUÇÃO POSSUI MAIS DE UM PARÂMETRO
                
                if (linha.contains(",")) {
                    
                    operandos = linha.split(",");
                    aux = operandos[0].split(" ");
                    
                    //System.out.println(operandos[0]);
                    flag_operandos = 1;
                }else
                    aux = linha.split(" ");
                
                //System.out.println(label[3]);
                operandos = linha.split(",");
                
                
                
                //---------------------------------------------------------------------------------------------------------------------------------------------
                //VERIFICA SE FICOU ALGUM ESPAÇO (" ") ANTES OU DEPOIS DE ALGUMA PALAVRA NO VETOR operandos
                if (flag_operandos == 1) {  
                    
                    for (int i = 1; i < operandos.length; i++) {
 
                        if (operandos[i].contains(" ")) 
                            operandos[i] = operandos[i].replace(" ", "");     
                    }
                }
                
                //VERIFICA SE FICOU ALGM ESPAÇO (" ") ANTES OU DEPOIS DE ALGUMA PALAVRA NO VETOR label
                for (int i = 0; i < aux.length; i++) {
                    
                    if (aux[i].contains(" "))    
                        aux[i] = aux[i].replace(" ", "");  
                }
                
                for (int i = 0; i < aux.length; i++) {
                    
                    if (!"".equals(aux[i])) 
                        k++;    
                }
                
                label = new String[k];
                k = 0;
                for (int i = 0; i < aux.length; i++) {
                    
                    if (!"".equals(aux[i])) {
                       
                        label[k] = aux[i];
                        k++;
                    }
                }
       
                
                
                
                //-----------------------------------------------------------------------------------------------------------------------------------------------
                
                
                
                
                if ((label.length == 3) && (!operandosMap.containsKey(label[0]))) {
                   
                   operandosMap.put(label[0], count_add+1);
                   pw.println(label[0] + "  " + (count_add+1));
                }
                
                //VERIFICA SE O PRIMEIRO OPERANDO JÁ FOI LIDO. SE NÃO FOI, É COLOCADO NA HASH
                if ((!operandosMap.containsKey(label[label.length - 1])) && (label[label.length - 1].contains("$"))) {
                    
                    if (j > 0) {
                        operandosMap.put(label[label.length - 1], count_add+2);
                        pw.println(label[label.length - 1] + "  " + (count_add+2));
                    } else {
                        
                        operandosMap.put(label[label.length - 1], count_add+1);
                        pw.println(label[label.length - 1] + "  " + (count_add+1));
                    }
                }
                //operandos = linha.split(", ");
                //VERIFICA SE EXISTE MAIS DE UM OPERANDO. SE TIVER, flag_operandos VALE 1.
                if (flag_operandos == 1) {
                    
                    for (int i = 1; i < operandos.length; i++) {
                        //System.out.println("hasuhas");
                        if ((!operandosMap.containsKey(operandos[i])) && (operandos[i].contains("$"))) {
                            
                            if (j > 0) {    
                                operandosMap.put(operandos[i], count_add+i+2);
                                pw.println(operandos[i] + "  " + (count_add+i+2));
                            } else {
                                
                                operandosMap.put(operandos[i], count_add+i+1);
                                pw.println(operandos[i] + "  " + (count_add+i+1));
                            }
                        }
                    }
                }
                //System.out.println(operandos.length);
                
                if (flag_operandos == 1 && j == 0) {
                    count_add = count_add + operandos.length;
                    //System.out.println(count_add);
                }else { 
                    if (flag_operandos == 1 && j > 0) {
                        
                        count_add = count_add + operandos.length + 1;
                        //System.out.println(count_add);
                    } else {
                        
                        count_add = count_add + label.length + 1;
                    }    
                }
                //System.out.println("hasuhas");
                flag_operandos = 0;
                j++;
                k = 0;
            }
            pw.close();
        }
        catch (Exception e){
            
            System.out.println("Erro ao abrir o arquivo do código");
        }
    }
    
    public void getAssembly() {
        
        String linha;
        String operandos[];
        String palavras[];
        String aux[];
        int i = 0 , flag_operandos = 0, k = 0;
        
        try {
            
            FileInputStream arquivo = new FileInputStream("codigo.txt");
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader instrucoes = new BufferedReader(input);
            
            FileOutputStream arquivo2 = new FileOutputStream("assembly.txt");
            PrintWriter pw = new PrintWriter(arquivo2);
            
            while ((linha = instrucoes.readLine()) != null) {
                
                //operandos = linha.split(", ");
                //palavras = operandos[0].split(" "); 
                   
                if (linha.contains(",")) {
                    
                    operandos = linha.split(",");
                    aux = operandos[0].split(" ");
                    
                    //System.out.println(operandos[0]);
                    flag_operandos = 1;
                }else
                    aux = linha.split(" ");
                
                operandos = linha.split(",");
                
                
                //-----------------------------------------------------------------------------------
                //VERIFICA SE FICOU ALGUM ESPAÇO (" ") ANTES OU DEPOIS DE ALGUMA PALAVRA NO VETOR operandos
                if (flag_operandos == 1) {  
                
                    for (int l= 1; l < operandos.length; l++) {
                    
                        if (operandos[l].contains(" ")) 
                            operandos[l] = operandos[l].replace(" ", "");
                    }
                }
                
                //VERIFICA SE FICOU ALGM ESPAÇO (" ") ANTES OU DEPOIS DE ALGUMA PALAVRA NO VETOR label
                for (int l = 0; l < aux.length; l++) {
                    
                    if (aux[l].contains(" ")) {
                        
                        aux[l] = aux[l].replace(" ", "");
                    }
                }
                
                for (int l = 0; l < aux.length; l++) {
                    
                    if (!"".equals(aux[l])) 
                        k++;  
                }
                palavras = new String[k];
                k = 0;
                for (int l = 0; l < aux.length; l++) {
                    
                    if (!"".equals(aux[l])) {

                        palavras[k] = aux[l];
                         k++;
                    }
                }
                
                
                
                //---------------------------------------------------------------------------------------------------------------------------------------------
                
                
                
                
                if (palavras.length == 3) {
                    
                    if (operandosMap.containsKey(palavras[0])) {
                        
                        pw.print(operandosMap.get(palavras[0]) + " ");
                    } else {
                        
                        System.out.println("Erro! Label não encontrado\nO código não pode ser montado");
                        break;
                    }
                    
                    if (montadorMap.containsKey(palavras[1])) {
                        
                        pw.print(montadorMap.get(palavras[1]) + " ");
                    } else {
                        
                       System.out.println("Erro! Instrução não encontrada\nO código não pode ser montado");
                       break; 
                    }
                    
                    if (operandosMap.containsKey(palavras[2])) {
                        
                        pw.print(operandosMap.get(palavras[2]) + " ");
                    } else {
                        
                        System.out.println("Erro! Operando 1 não encontrado\nO código não pode ser montado");
                        break;
                    }
                    
                } else {
                    
                    if (palavras.length == 2) {
                        
                        //System.out.println(palavras[0] + "teste");
                        //System.out.println(montadorMap.get(palavras[0]) + "teste");
                        if (montadorMap.containsKey(palavras[0])) {
                        
                            pw.print(montadorMap.get(palavras[0]) + " ");
                        } else {
                        
                            System.out.println("Erro! Instrução não encontrada\nO código não pode ser montado");
                            break; 
                        }
                    
                        if (operandosMap.containsKey(palavras[1])) {
                        
                            pw.print(operandosMap.get(palavras[1]) + " ");
                        } else {
                        
                            System.out.println("Erro! Operando 1 não encontrado\nO código não pode ser montado");
                            break;
                        }
     
                    } else 
                        System.out.println("Erro! Você está tentando utilizar mais de um label ou mais de uma instrução\nO código não pode ser montado");
                }
                
                
                
                for (i = 1; i < operandos.length; i++) {
                    
                    if (operandosMap.containsKey(operandos[i])) {
                        
                        pw.print(operandosMap.get(operandos[i]) + " ");
                    } else {
                        
                        System.out.println("Erro! Operando " + (i+1) + " não encontrado\nO código não pode ser montado");
                        break;
                    }
                } 
                pw.print("\n");
                k = 0;
                flag_operandos = 0;
            }
            pw.close();
        }
        catch (Exception e) {
            
            System.out.println("Erro ao gerar o código de máquina");
        }
    }
    
    public boolean verificaKey(String instrucao) {
        
        return montadorMap.containsKey(instrucao);
    }
}