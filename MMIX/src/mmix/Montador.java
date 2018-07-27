//ESTOU CONSIDERANDO QUE OS LABELS NÃO PODEM CONTER "$"
package trabalhops;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;

public class Montador {

    HashMap montadorMap = new HashMap();
    HashMap operandosMap = new HashMap();
    HashMap tabelaDeUso = new HashMap();
    HashMap tabelaDef = new HashMap();
    HashMap macroMap = new HashMap();
    

    public void getInstructions() {

        String linha;
        String palavras[] = new String[2];
        int i = 0;
        try {

            FileInputStream arquivo = new FileInputStream("instrucoes.txt");
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader instrucoes = new BufferedReader(input);

            while (i < 5) {

                linha = instrucoes.readLine();
                palavras = linha.split(" ");

                montadorMap.put(palavras[0], palavras[1]);

                i++;
            }
        } catch (Exception e) {

            System.out.println("Erro ao abrir o arquivo das instruções");
        }
    }

    public boolean getCode() {

        String linha;
        String operandos[];
        String label[];
        String aux[];
        String nome_codigo;
        String nome_tabelasimbolo;
        String nome_tabeladef;
        String nome_tabelauso;

        int count_add = 0, flag_operandos = 0, j = 0, k = 0, cont = 1, flag_break = 0, loop = 0, flag_nextline = 0, flag_macro = 0, cont_macro = 0;
        int nome_macro = 1;
        
        try {

            while (cont != 0) {
                nome_codigo = "codigo".concat(Integer.toString(cont)).concat(".txt");

                FileInputStream arquivo = new FileInputStream(nome_codigo);
                InputStreamReader input = new InputStreamReader(arquivo);
                BufferedReader instrucoes = new BufferedReader(input);

                nome_tabelasimbolo = "symbol_table".concat(Integer.toString(cont)).concat(".txt");
                FileOutputStream arquivo2 = new FileOutputStream(nome_tabelasimbolo);
                PrintWriter pw = new PrintWriter(arquivo2);
                
                nome_tabeladef = "definition_table".concat(Integer.toString(cont)).concat(".txt");
                FileOutputStream arquivo3 = new FileOutputStream(nome_tabeladef);
                PrintWriter def = new PrintWriter(arquivo3);
                
                nome_tabelauso = "use_table".concat(Integer.toString(cont)).concat(".txt");
                FileOutputStream arquivo4 = new FileOutputStream(nome_tabelauso);
                PrintWriter uso = new PrintWriter(arquivo4);

                pw.println("Símbolo | Endereço | Mod");

                while ((linha = instrucoes.readLine()) != null) {

                    // System.out.println(linha);
                    //VERIFICA SE A INSTRUÇÃO POSSUI MAIS DE UM PARÂMETRO
                    //do {
                    /*if () {
                            flag_break = 1;
                            break;
                        }*/
                    k = 0;
                    if (linha.contains(",")) {

                        operandos = linha.split(",");
                        aux = operandos[0].split(" ");

                        //System.out.println(operandos[0]);
                        flag_operandos = 1;
                    } else {
                        aux = linha.split(" ");
                    }

                    //System.out.println(label[3]);
                    operandos = linha.split(",");

                    //---------------------------------------------------------------------------------------------------------------------------------------------
                    //VERIFICA SE FICOU ALGUM ESPAÇO (" ") ANTES OU DEPOIS DE ALGUMA PALAVRA NO VETOR operandos
                    if (flag_operandos == 1) {

                        for (int i = 1; i < operandos.length; i++) {

                            if (operandos[i].contains(" ")) {
                                operandos[i] = operandos[i].replace(" ", "");
                            }
                        }
                    }

                    //VERIFICA SE FICOU ALGM ESPAÇO (" ") ANTES OU DEPOIS DE ALGUMA PALAVRA NO VETOR label
                    for (int i = 0; i < aux.length; i++) {

                        if (aux[i].contains(" ")) {
                            aux[i] = aux[i].replace(" ", "");
                        }
                    }

                    for (int i = 0; i < aux.length; i++) {

                        if (!"".equals(aux[i])) {
                            k++;
                        }
                    }

                    label = new String[k];
                    k = 0;

                    for (int i = 0; i < aux.length; i++) {

                        if (!"".equals(aux[i])) {

                            label[k] = aux[i];
                            k++;
                        }
                    }
                    //System.out.println(operandos[0]);    
                    // } while(operandos[0] == "INTUSE" || operandos[0] == "INTDEF");

                    //-----------------------------------------------------------------------------------------------------------------------------------------------
                    if ("MCEND".equals(label[0])) {
                        
                        flag_macro = 0;
                        cont_macro = 0;
                        flag_nextline = 1;
                    }
                   
                    if (flag_macro == 1) {
                        
                        if (cont_macro == 1) {
                            macroMap.put(label[label.length - 1], nome_macro); 
                            nome_macro++;
                        }
                        
                        if (cont_macro > 1)
                            getMacro(label, operandos, (nome_macro-1));
                        
                        cont_macro++;
                    }
                    
                    if ("MCDEFN".equals(label[0]) && k == 1) {
                        
                        flag_macro = 1;
                        cont_macro++;
                    }
    
                    if ("INTUSE".equals(label[0])) {
                        
                        tabelaDeUso.put(label[1], null);
                        flag_nextline = 1;
                    }
                    
                    if ("INTDEF".equals(label[0])) {
                        //System.out.println(label[1]);
                        tabelaDef.put(label[1], null);
                        flag_nextline = 1;
                    }

                    if (flag_nextline == 0 && flag_macro == 0) {
                        if ((label.length == 3) && (!operandosMap.containsKey(label[0]))) {

                            if (tabelaDeUso.containsKey(label[0])) {
                                
                                System.out.println("Você está tentando definir um símbolo cuja definição se dá em outro módulo");
                                return false;
                            } else {
                                
                                if (tabelaDef.containsKey(label[0])) {
                                    
                                    operandosMap.put(label[0], count_add + 1);
                                    tabelaDef.put(label[0], count_add + 1);
                                    def.println(label[0] + " " + (count_add + 1) + " r");
                                    //System.out.println(label[0]);
                                } else {
                                    
                                    
                                    operandosMap.put(label[0], count_add + 1);
                                    pw.println(label[0] + " " + (count_add + 1) + " r");
                                }
                            }
                            
                          
                        }

                        //VERIFICA SE O PRIMEIRO OPERANDO JÁ FOI LIDO. SE NÃO FOI, É COLOCADO NA HASH
                        if ((!operandosMap.containsKey(label[label.length - 1]))/* && (label[label.length - 1].contains("$"))*/) {

                            if (j > 0) {
                               
                                if (tabelaDeUso.containsKey(label[label.length - 1])) {
                                    
                                    tabelaDeUso.put(label[label.length - 1], count_add + 2);
                                    uso.println(label[label.length - 1] + " " + (count_add + 2));
                                } else {
                                
                                    operandosMap.put(label[label.length - 1], count_add + 2);
                                    
                                    if (tabelaDef.containsKey(label[label.length - 1]))
                                        def.println(label[label.length - 1] + " " + (count_add + 2) + " r ");
                                    else
                                        pw.println(label[label.length - 1] + " " + (count_add + 2) + " r ");
                                }
                            } else {
                                
                                if (tabelaDeUso.containsKey(label[label.length - 1])) {
                                    
                                    tabelaDeUso.put(label[label.length - 1], count_add + 1);
                                    uso.println(label[label.length - 1] + " " + (count_add + 1));
                                } else {
                                    
                                    operandosMap.put(label[label.length - 1], count_add + 1);
                                    
                                    if (tabelaDef.containsKey(label[label.length - 1]))
                                        def.println(label[label.length - 1] + " " + (count_add + 1) + " r ");
                                    else
                                        pw.println(label[label.length - 1] + " " + (count_add + 1) + " r ");
                                }
                            }
                        }

                        //operandos = linha.split(", ");
                        //VERIFICA SE EXISTE MAIS DE UM OPERANDO. SE TIVER, flag_operandos VALE 1.
                        if (flag_operandos == 1) {

                            for (int i = 1; i < operandos.length; i++) {
                                //System.out.println("hasuhas");
                                if ((!operandosMap.containsKey(operandos[i]))/* && (operandos[i].contains("$"))*/) {

                                    if (j > 0) {
                                        
                                        if (tabelaDeUso.containsKey(operandos[i])) {
                                            
                                            tabelaDeUso.put(operandos[i], count_add + 2 + i);
                                            uso.println(operandos[i] + " " + (count_add + 2 + i));
                                        } else {
                                            
                                            operandosMap.put(operandos[i], count_add + i + 2);
                                            
                                            if (tabelaDef.containsKey(operandos[i]))
                                                def.println(operandos[i] + " " + (count_add + 2 + i) + " r ");
                                            else    
                                                pw.println(operandos[i] + " " + (count_add + 2 + i) + " r ");
                                        }
                                    } else {

                                        if (tabelaDeUso.containsKey(operandos[i])) {
                                            
                                            tabelaDeUso.put(operandos[i], count_add + 1 + i);
                                            uso.println(operandos[i] + " " + (count_add + 1 + i));
                                        } else { 
                                            
                                            operandosMap.put(operandos[i], count_add + i + 1);
                                            
                                            if (tabelaDef.containsKey(operandos[i]))
                                                def.println(operandos[i] + " " + (count_add + 1 + i) + " r ");
                                            else    
                                                pw.println(operandos[i] + " " + (count_add + i + 1) + " r ");
                                        }
                                    }
                                }
                            }
                        }
                        //System.out.println(operandos.length);

                        if (flag_operandos == 1 && j == 0) {
                            count_add = count_add + operandos.length;
                            //System.out.println(count_add);
                        } else {
                            if (flag_operandos == 1 && j > 0) {

                                count_add = count_add + operandos.length + 1;
                                //System.out.println(count_add);
                            } else {
//System.out.println(count_add);
                                if (j == 0)    
                                    count_add = count_add + label.length;
                                else 
                                   count_add = count_add + label.length; 
                                //System.out.println(label.length);
                            }
                        }
                        j++;
                    }
                    //System.out.println("hasuhas");
                    flag_operandos = 0;
                    flag_nextline = 0;
                    k = 0;
                    
                }
                //System.out.println("\n");
                //System.out.println("hsahsauashu");
                pw.close();
                def.close();
                uso.close();
                getAssembly(cont);
                nome_macro = 1;
                cont++;
                count_add = -1;
                flag_operandos = 0;
                
                k = 0;
                operandosMap.clear();
                tabelaDeUso.clear();
                tabelaDef.clear();
            }
        } catch (Exception e) {

            //System.out.println("Erro ao abrir o arquivo do código");
        }
        return true;
    }

    public void getAssembly(int cont) {

        String linha;
        String operandos[];
        String palavras[];
        String aux[];
        String nome_codigo;
        String nome_assembly;

        int i = 0, flag_operandos = 0, k = 0, flag_break = 0, loop = 0, flag_nextline = 0;

        try {

            nome_codigo = "codigo".concat(Integer.toString(cont)).concat(".txt");

            FileInputStream arquivo = new FileInputStream(nome_codigo);
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader instrucoes = new BufferedReader(input);

            nome_assembly = "assembly".concat(Integer.toString(cont)).concat(".txt");

            FileOutputStream arquivo2 = new FileOutputStream(nome_assembly);
            PrintWriter pw = new PrintWriter(arquivo2);

            while ((linha = instrucoes.readLine()) != null) {

                //operandos = linha.split(", ");
                //palavras = operandos[0].split(" "); 
                //do {   
                /*if () {
                        flag_break = 1;
                        break;
                    }*/
                k = 0;
                if (linha.contains(",")) {

                    operandos = linha.split(",");
                    aux = operandos[0].split(" ");

                    //System.out.println(operandos[0]);
                    flag_operandos = 1;
                } else {
                    aux = linha.split(" ");
                }

                operandos = linha.split(",");

                //-----------------------------------------------------------------------------------
                //VERIFICA SE FICOU ALGUM ESPAÇO (" ") ANTES OU DEPOIS DE ALGUMA PALAVRA NO VETOR operandos
                if (flag_operandos == 1) {

                    for (int l = 1; l < operandos.length; l++) {

                        if (operandos[l].contains(" ")) {
                            operandos[l] = operandos[l].replace(" ", "");
                        }
                    }
                }

                //VERIFICA SE FICOU ALGM ESPAÇO (" ") ANTES OU DEPOIS DE ALGUMA PALAVRA NO VETOR label
                for (int l = 0; l < aux.length; l++) {

                    if (aux[l].contains(" ")) {

                        aux[l] = aux[l].replace(" ", "");
                    }
                }
                
                for (int l = 0; l < aux.length; l++) {

                    if (!"".equals(aux[l])) {
                        k++;
                    }
                }
                palavras = new String[k];
                k = 0;
                for (int l = 0; l < aux.length; l++) {

                    if (!"".equals(aux[l])) {

                        palavras[k] = aux[l];
                        k++;
                    }
                }
                //} while (palavras[0] == "INTUSE" || palavras[0] == "INTDEF");

                if ("INTUSE".equals(palavras[0]) || "INTDEF".equals(palavras[0])) {

                    flag_nextline = 1;
                }

                //---------------------------------------------------------------------------------------------------------------------------------------------
                if (flag_nextline == 0) {
                    if (palavras.length == 3) {

                        /*if (operandosMap.containsKey(palavras[0])) {

                            pw.print(operandosMap.get(palavras[0]) + " ");
                        } else {

                            System.out.println("Erro! Label não encontrado\nO código não pode ser montado");
                            break;
                        }*/
                        //System.out.println(palavras[1]);
                        if (montadorMap.containsKey(palavras[1])) {

                            pw.print(montadorMap.get(palavras[1]) + " a ");
                        } else {
                            System.out.println(montadorMap.get(palavras[i]));
                            System.out.println("Erro! Instrução não encontrada\nO código não pode ser montado");
                            break;
                        }

                        if (operandosMap.containsKey(palavras[2])) {

                            pw.print(operandosMap.get(palavras[2]) + " r ");
                        } else {
                            
                            if (tabelaDeUso.containsKey(palavras[2])) {
                                
                                pw.print("00 a ");
                            } else {
                                System.out.println("Erro! Operando 1 não encontrado\nO código não pode ser montado");
                                break;
                            }
                        }
                         
                    } else {

                        if (palavras.length == 2) {
                         
                       
                            if (montadorMap.containsKey(palavras[0])) {

                                pw.print(montadorMap.get(palavras[0]) + " r ");
                            } else {

                                System.out.println("Erro! Instrução não encontrada\nO código não pode ser montado");
                                break;
                            }

                            if (operandosMap.containsKey(palavras[1])) {

                                pw.print(operandosMap.get(palavras[1]) + " r ");
                            } else {
                                
                                if (tabelaDeUso.containsKey(palavras[1])) {
                                    
                                   pw.print("00 a "); 
                                } else {
                                
                                    System.out.println("Erro! Operando 1 não encontrado\nO código não pode ser montado");
                                    break;
                                }
                            }
             
                        } else {
                            System.out.println("Erro! Você está tentando utilizar mais de um label ou mais de uma instrução\nO código não pode ser montado");
                        }
                    }

                    for (i = 1; i < operandos.length; i++) {

                        if (operandosMap.containsKey(operandos[i])) {

                            pw.print(operandosMap.get(operandos[i]) + " r ");
                        } else {
                            
                            if (tabelaDeUso.containsKey(operandos[i])) {
                                
                                pw.print("00 a ");
                            } else {
                                
                                System.out.println("Erro! Operando " + (i + 1) + " não encontrado\nO código não pode ser montado");
                                break;
                            }
                        }
                    }
                    pw.print("\n");
                }
                k = 0;
                flag_operandos = 0;
                flag_nextline = 0;
            }
            pw.close();
            System.out.println("husahuasasu");
        } catch (Exception e) {

            System.out.println("Erro ao gerar o código de máquina");
        }
    }

    public void getMacro(String label[], String operandos[], int nome_macro) {
        
        try {
            //FileOutputStream arquivo2 = new FileOutputStream("macro.txt");
            //PrintWriter pw = new PrintWriter(arquivo2);
           
            Writer pw = new BufferedWriter(new FileWriter("macro".concat(Integer.toString(nome_macro)).concat(".txt") , true));

            int i;

            for (i = 0; i < label.length; i++) {
                
                if (i == (label.length - 1))
                    pw.append(label[i]);
                else
                    pw.append(label[i] + " "); 
            }

            for (i = 1; i < operandos.length; i++) {
                
                if (i == (operandos.length - 1))
                    pw.append(operandos[i]);
                else
                    if (i == 1)
                        pw.append(", " + operandos[i] + ", ");
                    else 
                        pw.append(operandos[i] + ", ");
            }
            pw.append("\n");
            pw.close();
        }
        catch (Exception e) {
            
        }
        
    }
        
    public boolean verificaKey(String instrucao) {

        return montadorMap.containsKey(instrucao);
    }
}
