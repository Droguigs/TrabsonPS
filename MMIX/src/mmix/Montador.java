//ESTOU CONSIDERANDO QUE OS LABELS NÃƒO PODEM CONTER "$"
package trabalhops;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class Montador {

    private HashMap<String, Integer> montadorMap = new HashMap<>();
    private HashMap operandosMap = new HashMap();
    private HashMap tabelaDeUso = new HashMap();
    private HashMap tabelaDef = new HashMap();
    private HashMap macroOperandos = new HashMap();
    Map<String, Integer> macroMap = new HashMap<>();
    ProcessadorDeMacros macro = new ProcessadorDeMacros();

    public void getInstructions() {

        String linha;
        String palavras[] = new String[2];
        int i = 0, num;
        try {

            FileInputStream arquivo = new FileInputStream("instrucoes.txt");
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader instrucoes = new BufferedReader(input);
            
            while (i < 45) {

                linha = instrucoes.readLine();
                palavras = linha.split(" ");
                //num = Integer.parseInt(palavras[1]);
                //System.out.println();
                //num = Integer.decode(palavras[1]);
                
                montadorMap.put(palavras[0], Integer.decode(palavras[1]));
 
                i++;
            }
        } catch (Exception e) {

            System.out.println("Erro ao abrir o arquivo das instruÃ§Ãµes");
        }
    }

    private int parseInt(String str) {
        int num;
        if(str.length() >= 2 && str.substring(0, 2).equals("0x")) {
            str = str.substring(2);
            num = Integer.parseInt(str, 16);
        }
        else {
            num = Integer.parseInt(str);
        }

        return num;
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
        try {
            FileOutputStream arquivo10 = new FileOutputStream("macro1.txt");
        } catch (Exception r) {
            
        }
        PrintWriter pw_macro;
        int valor;

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
         

                pw.println("SÃ­mbolo | EndereÃ§o");
                
                
                
                while ((linha = instrucoes.readLine()) != null) {
          
                    
                    k = 0;
                    if (linha.contains(",")) {

                        operandos = linha.split(",");
                        aux = operandos[0].split(" ");

                        
                        flag_operandos = 1;
                    } else {
                        aux = linha.split(" ");
                    }

                    operandos = linha.split(",");
                    
                    //---------------------------------------------------------------------------------------------------------------------------------------------
                    //VERIFICA SE FICOU ALGUM ESPAÃ‡O (" ") ANTES OU DEPOIS DE ALGUMA PALAVRA NO VETOR operandos
                    if (flag_operandos == 1) {

                        for (int i = 1; i < operandos.length; i++) {

                            if (operandos[i].contains(" ")) {
                                operandos[i] = operandos[i].replace(" ", "");
                            }
                        }
                    }

                    //VERIFICA SE FICOU ALGM ESPAÃ‡O (" ") ANTES OU DEPOIS DE ALGUMA PALAVRA NO VETOR label
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
                  
                    //-----------------------------------------------------------------------------------------------------------------------------------------------
                    if ("MCEND".equals(label[0])) {

                        flag_macro = 0;
                        cont_macro = 0;
                        flag_nextline = 1;
                    }
                    
                    if (label.length > 1 && flag_macro == 0) {
                        if (macroMap.containsKey(label[label.length - 2])) {

                            count_add = getMacroCode(j, pw, def, uso, macroMap.get(label[label.length - 2]), count_add, label, operandos);
                            
                            if (j == 0)
                                j++;
                            
                            flag_nextline = 1;
                        }
                    }

                    if (flag_macro == 1) {

                        if (cont_macro == 1) {
                            macroMap.put(label[label.length - 2], nome_macro);

                            nome_macro++;
                            
                        }

                        
                        macro.getMacro(label, operandos, (nome_macro - 1), linha);
                        
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
                        
                        tabelaDef.put(label[1], null);
                        flag_nextline = 1;
                    }
                   
                    if (flag_nextline == 0 && flag_macro == 0) {
                        if ((label.length == 3) && (!operandosMap.containsKey(label[0]))) {
 
                            if (tabelaDeUso.containsKey(label[0])) {

                                System.out.println("VocÃª estÃ¡ tentando definir um sÃ­mbolo cuja definiÃ§Ã£o se dÃ¡ em outro mÃ³dulo");
                                System.exit(1);
                                return false;
                            } else {

                                if (tabelaDef.containsKey(label[0])) {

                                    operandosMap.put(label[0], count_add + 1);
                                    tabelaDef.put(label[0], count_add + 1);
                                    def.println(label[0] + " " + (count_add + 1));
                                    
                                } else {

                                    operandosMap.put(label[0], count_add + 1);
                                    pw.println(label[0] + " " + (count_add + 1));
                                }
                            }
                        }
                   
                        //VERIFICA SE O PRIMEIRO OPERANDO JÃ� FOI LIDO. SE NÃƒO FOI, Ã‰ COLOCADO NA HASH
                        if ((!operandosMap.containsKey(label[label.length - 1]))/* && (label[label.length - 1].contains("$"))*/) {

                            if (!label[label.length - 1].contains("$") && (("BP".equals(label[0])) || ("BNZ".equals(label[0])) || ("BNN".equals(label[0])) || ("BOD".equals(label[0])) || ("BEV".equals(label[0])) || ("JMP".equals(label[0])))) {

                            } else {
                                if (j > 0) {

                                    if (tabelaDeUso.containsKey(label[label.length - 1])) {

                                        tabelaDeUso.put(label[label.length - 1], count_add + 2);
                                        uso.println(label[label.length - 1] + " " + (count_add + 2));
                                    } else {

                                        operandosMap.put(label[label.length - 1], count_add + 2);

                                        if (tabelaDef.containsKey(label[label.length - 1])) {
                                            def.println(label[label.length - 1] + " " + (count_add + 2));
                                        } else {
                                            pw.println(label[label.length - 1] + " " + (count_add + 2));

                                        }
                                    }
                                } else {

                                    if (tabelaDeUso.containsKey(label[label.length - 1])) {

                                        tabelaDeUso.put(label[label.length - 1], count_add + 1);
                                        uso.println(label[label.length - 1] + " " + (count_add + 1));
                                    } else {

                                        operandosMap.put(label[label.length - 1], count_add + 1);

                                        if (tabelaDef.containsKey(label[label.length - 1])) {
                                            def.println(label[label.length - 1] + " " + (count_add + 1));
                                        } else {
                                            pw.println(label[label.length - 1] + " " + (count_add + 1));
                                        }
                                    }
                                }
                            }
                        }
                            //VERIFICA SE EXISTE MAIS DE UM OPERANDO. SE TIVER, flag_operandos VALE 1.
                            if (flag_operandos == 1) {
                      
                                
                                for (int i = 1; i < operandos.length; i++) {
                                    
                                    if ((!operandosMap.containsKey(operandos[i]))) {

                                        if (!operandos[i].contains("$") && (("BP".equals(label[0])) || ("BNZ".equals(label[0])) || ("BNN".equals(label[0])) || ("BOD".equals(label[0])) || ("BEV".equals(label[0])) || ("JMP".equals(label[0])))) {

                                        } else {
                                         
                                            if (j > 0) {

                                                if (tabelaDeUso.containsKey(operandos[i])) {

                                                    tabelaDeUso.put(operandos[i], count_add + 2 + i);
                                                    uso.println(operandos[i] + " " + (count_add + 2 + i));
                                                } else {

                                                    operandosMap.put(operandos[i], count_add + i + 2);

                                                    if (tabelaDef.containsKey(operandos[i])) {
                                                            def.println(operandos[i] + " " + (count_add + 2 + i));
                                                    } else {
                                                        pw.println(operandos[i] + " " + (count_add + 2 + i));
                                                    }
                                                }
                                            } else {

                                                if (tabelaDeUso.containsKey(operandos[i])) {

                                                    tabelaDeUso.put(operandos[i], count_add + 1 + i);
                                                    uso.println(operandos[i] + " " + (count_add + 1 + i));
                                                } else {

                                                    operandosMap.put(operandos[i], count_add + i + 1);

                                                    if (tabelaDef.containsKey(operandos[i])) {
                                                        def.println(operandos[i] + " " + (count_add + 1 + i));
                                                    } else {
                                                        pw.println(operandos[i] + " " + (count_add + i + 1));
                                                    }
                                                }
                                            }
                                        }
                                    } 
                                }
                            }
                            //System.out.println(operandos.length);

                            if (flag_operandos == 1 && j == 0) {
                                count_add = count_add + operandos.length;
                                
                            } else {
                                if (flag_operandos == 1 && j > 0) {

                                    count_add = count_add + operandos.length + 1;
                                    
                                } else {

                                    if (j == 0) {
                                        count_add = count_add + label.length;
                                    } else {

                                        if (label.length == 3) {

                                            count_add = count_add + label.length - 1;
                                        } else {
                                            count_add = count_add + label.length;
                                        }
                                    }
                                }
                            }
                            
                            j++;
                        }
                    
                    flag_operandos = 0;
                    flag_nextline = 0;
                    k = 0;
                    }
             
                pw.close();
                def.close();
                uso.close();
                getAssembly(cont);
                nome_macro++;
                cont++;
                count_add = -1;
                flag_operandos = 0;
                deleteMacro();
                k = 0;
                operandosMap.clear();
                tabelaDeUso.clear();
                tabelaDef.clear();
                macroOperandos.clear();
                macroMap.clear();
            }
        }
        catch (Exception e) {

            //System.out.println("Erro ao abrir o arquivo do cÃ³digo");
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

        int i = 0, flag_operandos = 0, k = 0, flag_break = 0, loop = 0, flag_nextline = 0, flag_macro = 0;

        try {

            nome_codigo = "codigo".concat(Integer.toString(cont)).concat(".txt");

            FileInputStream arquivo = new FileInputStream(nome_codigo);
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader instrucoes = new BufferedReader(input);

            nome_assembly = "assembly".concat(Integer.toString(cont)).concat(".txt");

            FileOutputStream arquivo2 = new FileOutputStream(nome_assembly);
            PrintWriter pw = new PrintWriter(arquivo2);

            while ((linha = instrucoes.readLine()) != null) {

                k = 0;
                if (linha.contains(",")) {

                    operandos = linha.split(",");
                    aux = operandos[0].split(" ");

                    
                    flag_operandos = 1;
                } else {
                    aux = linha.split(" ");
                }

                operandos = linha.split(",");

                //-----------------------------------------------------------------------------------
                //VERIFICA SE FICOU ALGUM ESPAÃ‡O (" ") ANTES OU DEPOIS DE ALGUMA PALAVRA NO VETOR operandos
                if (flag_operandos == 1) {

                    for (int l = 1; l < operandos.length; l++) {

                        if (operandos[l].contains(" ")) {
                            operandos[l] = operandos[l].replace(" ", "");
                        }
                    }
                }

                //VERIFICA SE FICOU ALGM ESPAÃ‡O (" ") ANTES OU DEPOIS DE ALGUMA PALAVRA NO VETOR label
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

                if (palavras.length > 1 && flag_macro == 0) {
                    if (macroMap.containsKey(palavras[palavras.length - 2])) {

                        pw = macro.getMacroAssembly(pw, macroMap.get(palavras[palavras.length - 2]), palavras, operandos, operandosMap, montadorMap, macroOperandos, tabelaDeUso);
                        flag_nextline = 1;
                    }
                }

                if ("INTUSE".equals(palavras[0]) || "INTDEF".equals(palavras[0])) {

                    flag_nextline = 1;
                }

                if ("MCEND".equals(palavras[0])) {

                    flag_macro = 0;
                    flag_nextline = 1;
                }

                if ("MCDEFN".equals(palavras[0])) {

                    flag_macro = 1;
                }

                //---------------------------------------------------------------------------------------------------------------------------------------------
                if (flag_nextline == 0 && flag_macro == 0) {
                    if (palavras.length == 3) {

                        //System.out.println(palavras[1]);
                        if (montadorMap.containsKey(palavras[1])) {

                            pw.print(montadorMap.get(palavras[1]) + " ");
                        } else {
                            System.out.println(montadorMap.get(palavras[i]));
                            System.out.println("Erro! InstruÃ§Ã£o nÃ£o encontrada\nO cÃ³digo nÃ£o pode ser montado");
                            System.exit(1);
                            break;
                        }

                        if (operandosMap.containsKey(palavras[2])) {

                            pw.print(operandosMap.get(palavras[2]) + " ");
                        } else {

                            if (tabelaDeUso.containsKey(palavras[2])) {

                                pw.print("00 ");
                            } else {
                                System.out.println("Erro! Operando 1 nÃ£o encontrado\nO cÃ³digo nÃ£o pode ser montado");
                                System.exit(1);
                                break;
                            }
                        }

                    } else {

                        if (palavras.length == 2) {

                            if (montadorMap.containsKey(palavras[0])) {

                                pw.print(montadorMap.get(palavras[0]) + " ");
                            } else {

                                System.out.println("Erro! InstruÃ§Ã£o nÃ£o encontrada\nO cÃ³digo nÃ£o pode ser montado");
                                System.exit(1);
                                break;
                            }

                            if (operandosMap.containsKey(palavras[1])) {

                                pw.print(operandosMap.get(palavras[1]) + " ");
                            } else {

                                if (tabelaDeUso.containsKey(palavras[1])) {

                                    pw.print("00 ");
                                } else {

                                    System.out.println("Erro! Operando 1 nÃ£o encontrado\nO cÃ³digo nÃ£o pode ser montado");
                                    System.exit(1);
                                    break;
                                }
                            }

                        } else {
                            System.out.println("Erro! VocÃª estÃ¡ tentando utilizar mais de um label ou mais de uma instruÃ§Ã£o\nO cÃ³digo nÃ£o pode ser montado");
                        }
                    }

                    for (i = 1; i < operandos.length; i++) {

                        if (operandosMap.containsKey(operandos[i])) {

                            pw.print(operandosMap.get(operandos[i]) + " ");
                        } else {

                            if (tabelaDeUso.containsKey(operandos[i])) {

                                pw.print("00 ");
                            } else {
                                System.out.println(cont);
                                System.out.println("Erro! Operando " + (i + 1) + " nÃ£o encontrado\nO cÃ³digo nÃ£o pode ser montado");
                                System.exit(1);
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

        } catch (Exception e) {
            System.exit(1);
            System.out.println("Erro ao gerar o cÃ³digo de mÃ¡quina");
        }
    }

    public int getMacroCode(int j, PrintWriter pw, PrintWriter def, PrintWriter uso, int nome_macro, int count_add, String label1[], String operandos1[]) {

        String linha;
        String nome_codigo;
        String operandos[];
        String label[];

        int flag_operandos = 0, k = 0, cont = 1, flag_break = 0, loop = 0, flag_nextline = 0, flag_macro = 0, cont_macro = 0, contador = 0;

        try {
            nome_codigo = "macro".concat(Integer.toString(nome_macro)).concat(".txt");

            FileInputStream arquivo = new FileInputStream(nome_codigo);
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader instrucoes = new BufferedReader(input);

            while ((linha = instrucoes.readLine()) != null) {

                if (linha.contains(",")) {

                    operandos = linha.split(", ");
                    label = operandos[0].split(" ");
                    flag_operandos = 1;
                } else {

                    label = linha.split(" ");
                }
                operandos = linha.split(", ");

                if (contador == 0) {

                    if (label.length == 3) {

                        macroOperandos.put(label[0], label1[0]);
                        macroOperandos.put(label[2], label1[2]);
                    } else {
                        macroOperandos.put(label[1], label1[1]);
                    }
                    
                    for (int i = 1; i < operandos.length; i++) {
                        macroOperandos.put(operandos[i], operandos1[i]);
                       
                    }
                    flag_nextline = 1;
                }

                if (flag_nextline == 0) {
                    if ((label.length == 3) && (!operandosMap.containsKey(macroOperandos.get(label[0])))) {

                        if (tabelaDef.containsKey(macroOperandos.get(label[0]))) {

                            operandosMap.put(macroOperandos.get(label[0]), count_add + 1);
                            tabelaDef.put(macroOperandos.get(label[0]), count_add + 1);
                            def.println(macroOperandos.get(label[0]) + " " + (count_add + 1));
                          
                        } else {

                            operandosMap.put(macroOperandos.get(label[0]), count_add + 1);
                            pw.println(macroOperandos.get(label[0]) + " " + (count_add + 1));
                        }
                    }

                    //VERIFICA SE O PRIMEIRO OPERANDO JÃ� FOI LIDO. SE NÃƒO FOI, Ã‰ COLOCADO NA HASH
                    if ((!operandosMap.containsKey(macroOperandos.get(label[label.length - 1]))) /*&& (label[label.length - 1].contains("$"))*/) {

                        if (j > 0) {

                            if (tabelaDeUso.containsKey(macroOperandos.get((label[label.length - 1])))) {

                                tabelaDeUso.put(macroOperandos.get(label[label.length - 1]), count_add + 2);
                                uso.println(macroOperandos.get(label[label.length - 1]) + " " + (count_add + 2));
                            } else {

                                operandosMap.put(macroOperandos.get(label[label.length - 1]), count_add + 2);

                                if (tabelaDef.containsKey(macroOperandos.get(label[label.length - 1]))) {
                                    def.println(macroOperandos.get(label[label.length - 1]) + " " + (count_add + 2));
                                } else {
                                    pw.println(macroOperandos.get(label[label.length - 1]) + " " + (count_add + 2));

                                }
                            }
                        } else {

                            if (tabelaDeUso.containsKey(macroOperandos.get(label[label.length - 1]))) {

                                tabelaDeUso.put(macroOperandos.get(label[label.length - 1]), count_add + 1);
                                uso.println(macroOperandos.get(label[label.length - 1]) + " " + (count_add + 1));
                            } else {

                                operandosMap.put(macroOperandos.get(label[label.length - 1]), count_add + 1);

                                if (tabelaDef.containsKey(macroOperandos.get(label[label.length - 1]))) {
                                    def.println(macroOperandos.get(label[label.length - 1]) + " " + (count_add + 1));
                                } else {
                                    pw.println(macroOperandos.get(label[label.length - 1]) + " " + (count_add + 1));
                                }
                            }
                        }
                    }

                    if (flag_operandos == 1) {
                        
                        for (int i = 1; i < operandos.length; i++) {
                          
                            if ((!operandosMap.containsKey(macroOperandos.get(operandos[i])))/* && (operandos[i].contains("$"))*/) {

                                if (j > 0) {

                                    if (tabelaDeUso.containsKey(macroOperandos.get(operandos[i]))) {

                                        tabelaDeUso.put(macroOperandos.get(operandos[i]), count_add + 2 + i);
                                        uso.println(macroOperandos.get(operandos[i]) + " " + (count_add + 2 + i));
                                    } else {

                                        operandosMap.put(macroOperandos.get(operandos[i]), count_add + i + 2);

                                        if (tabelaDef.containsKey(macroOperandos.get(operandos[i]))) {
                                            def.println(macroOperandos.get(operandos[i]) + " " + (count_add + 2 + i));
                                        } else {
                                            pw.println(macroOperandos.get(operandos[i]) + " " + (count_add + 2 + i));
                                        }
                                    }
                                } else {

                                    if (tabelaDeUso.containsKey(macroOperandos.get(operandos[i]))) {

                                        tabelaDeUso.put(macroOperandos.get(operandos[i]), count_add + 1 + i);
                                        uso.println(macroOperandos.get(operandos[i]) + " " + (count_add + 1 + i));
                                    } else {

                                        operandosMap.put(macroOperandos.get(operandos[i]), count_add + i + 1);

                                        if (tabelaDef.containsKey(macroOperandos.get(macroOperandos.get(operandos[i])))) {
                                            def.println(macroOperandos.get(operandos[i]) + " " + (count_add + 1 + i));
                                        } else {
                                            pw.println(macroOperandos.get(operandos[i]) + " " + (count_add + i + 1));
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (flag_operandos == 1 && j == 0) {
                        count_add = count_add + operandos.length;
                        
                    } else {
                        if (flag_operandos == 1 && j > 0) {

                            count_add = count_add + operandos.length + 1;
                           
                        } else {
                            
                            if (j == 0) {
                                count_add = count_add + label.length;
                                System.out.println(count_add);
                            } else {

                                if (label.length == 3) {

                                    count_add = count_add + label.length - 1;
                                } else {
                                    count_add = count_add + label.length;
                                }
                            }  
                        }
                    }
                    j++;
                }

                flag_operandos = 0;
                flag_nextline = 0;
                k = 0;
                contador++;
            }
        } catch (Exception e) {

        }

        return count_add;
    }

    public void deleteMacro() {
        
        int value;
       
        for (String key : macroMap.keySet()) {
            
            value = macroMap.get(key);
            
            File file = new File("macro".concat(Integer.toString(value)).concat(".txt"));
            file.delete();
        }
    }

    public boolean verificaKey(String instrucao) {

        return montadorMap.containsKey(instrucao);
    }
}
