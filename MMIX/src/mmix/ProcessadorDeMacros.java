package trabalhops;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.HashMap;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

public class ProcessadorDeMacros {

    public void getMacro(String label[], String operandos[], int nome_macro, String linha) {

        try {
            //FileOutputStream arquivo2 = new FileOutputStream("macro.txt");
            //PrintWriter pw = new PrintWriter(arquivo2);

            Writer pw = new BufferedWriter(new FileWriter("macro".concat(Integer.toString(nome_macro)).concat(".txt"), true));

            int i;

            /*for (i = 0; i < label.length; i++) {
                
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
                        pw.append(", " + operandos[i] + ", ");
            }*/
            pw.append(linha);
            pw.append("\n");
            pw.close();
        } catch (Exception e) {
            System.out.println("ashuash");
        }

    }

    public PrintWriter getMacroAssembly(PrintWriter pw, int nome_macro, String label1[], String operandos1[], Map<String, Integer> macroMap, HashMap operandosMap, HashMap montadorMap, HashMap macroOperandos, HashMap tabelaDeUso) {

        String nome_codigo;
        String linha;
        String palavras[];
        String operandos[];
        nome_codigo = "macro".concat(Integer.toString(nome_macro)).concat(".txt");
        int i = 0, flag_operandos = 0, k = 0, flag_break = 0, loop = 0, flag_nextline = 0, flag_macro = 0;

        try {
            FileInputStream arquivo = new FileInputStream(nome_codigo);
            InputStreamReader input = new InputStreamReader(arquivo);
            BufferedReader instrucoes = new BufferedReader(input);

            linha = instrucoes.readLine();

            while ((linha = instrucoes.readLine()) != null) {
                
                if (linha.contains(",")) {

                    operandos = linha.split(", ");
                    palavras = operandos[0].split(" ");
                    flag_operandos = 1;
                } else {

                    palavras = linha.split(" ");
                }
                operandos = linha.split(", ");
                
                if (flag_nextline == 0 && flag_macro == 0) {
                    if (palavras.length == 3) {

                        /*if (operandosMap.containsKey(palavras[0])) {
                            pw.print(operandosMap.get(palavras[0]) + " ");
                        } else {
                            System.out.println("Erro! Label não encontrado\nO código não pode ser montado");
                            break;
                        }*/
                        //System.out.println(palavras[1]);
                        if (montadorMap.containsKey(macroOperandos.get(palavras[1]))) {

                            pw.print(montadorMap.get(macroOperandos.get(palavras[1])) + " a ");
                        } else {
                            //System.out.println(montadorMap.get(palavras[i]));
                            System.out.println("Erro! Instrução não encontrada\nO código não pode ser montado");
                            break;
                        }

                        if (operandosMap.containsKey(macroOperandos.get(palavras[2]))) {

                            pw.print(operandosMap.get(macroOperandos.get(palavras[2])) + " r ");
                        } else {

                            if (tabelaDeUso.containsKey(macroOperandos.get(palavras[2]))) {

                                pw.print("00 a ");
                            } else {
                                System.out.println("Erro! Operando 1 não encontrado\nO código não pode ser montado");
                                break;
                            }
                        }

                    } else {

                        if (palavras.length == 2) {

                            if (montadorMap.containsKey(macroOperandos.get(palavras[0]))) {

                                pw.print(montadorMap.get(macroOperandos.get(palavras[0])) + " r ");
                            } else {

                                System.out.println("Erro! Instrução não encontrada\nO código não pode ser montado");
                                break;
                            }

                            if (operandosMap.containsKey(macroOperandos.get(palavras[1]))) {

                                pw.print(operandosMap.get(macroOperandos.get(palavras[1])) + " r ");
                            } else {

                                if (tabelaDeUso.containsKey(macroOperandos.get(palavras[1]))) {

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

                        if (operandosMap.containsKey(macroOperandos.get(palavras[i]))) {

                            pw.print(operandosMap.get(macroOperandos.get(palavras[i])) + " r ");
                        } else {

                            if (tabelaDeUso.containsKey(macroOperandos.get(palavras[i]))) {

                                pw.print("00 a ");
                            } else {

                                System.out.println("Erro! Operando " + (i + 1) + " não encontrado\nO código não pode ser montado");
                                break;
                            }
                        }
                    }
                    pw.print("\n");
                }
                flag_operandos = 0;
                flag_nextline = 0;
            }
            
            
        }
        catch (Exception e) {
            
        }
        return pw;
    }
}
