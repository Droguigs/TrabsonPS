package trabalhops;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
            
            Writer pw = new BufferedWriter(new FileWriter("macro".concat(Integer.toString(nome_macro)).concat(".txt"), true));
            int i;

            pw.append(linha);
            pw.append("\n");
            pw.close();
        } catch (Exception e) {
            System.out.println("ashuash");
        }
        

    }
                                                        
    public PrintWriter getMacroAssembly(PrintWriter pw, int nome_macro, String label1[], String operandos1[], HashMap operandosMap, HashMap<String, Integer> montadorMap, HashMap macroOperandos, HashMap tabelaDeUso) {
        
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

            instrucoes.readLine();
            
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

                        if (montadorMap.containsKey((palavras[1]))) {

                            pw.print(montadorMap.get((palavras[1])) + " ");
                        } else {
                          
                            System.out.println("Erro! InstruÃ§Ã£o na macro nÃ£o encontrada\nO cÃ³digo nÃ£o pode ser montado");
                            System.exit(1);
                            break;
                        }

                        if (operandosMap.containsKey(macroOperandos.get(palavras[2]))) {

                            pw.print(operandosMap.get(macroOperandos.get(palavras[2])) + " ");
                        } else {

                            if (tabelaDeUso.containsKey(macroOperandos.get(palavras[2]))) {

                                pw.print("00 ");
                            } else {
                                System.out.println("Erro! Operando 1 na macro nÃ£o encontrado\nO cÃ³digo nÃ£o pode ser montado");
                                System.exit(1);
                                break;
                            }
                        }

                    } else {

                        if (palavras.length == 2) {
                           
                            if (montadorMap.containsKey(palavras[0])) {
                                
                                pw.print(montadorMap.get((palavras[0])) + " ");
                            } else {

                                System.out.println("Erro! InstruÃ§Ã£o na macro nÃ£o encontrada\nO cÃ³digo nÃ£o pode ser montado");
                                System.exit(1);
                                break;
                            }

                            if (operandosMap.containsKey(macroOperandos.get(palavras[1]))) {

                                pw.print(operandosMap.get(macroOperandos.get(palavras[1])) + " ");
                            } else {

                                if (tabelaDeUso.containsKey(macroOperandos.get(palavras[1]))) {

                                    pw.print("00 ");
                                } else {

                                    System.out.println("Erro! Operando 1 na macro nÃ£o encontrado\nO cÃ³digo nÃ£o pode ser montado");
                                    System.exit(1);
                                    break;
                                }
                            }

                        } else {
                            System.out.println("Erro! VocÃª estÃ¡ tentando utilizar mais de um label ou mais de uma instruÃ§Ã£o na macro\nO cÃ³digo nÃ£o pode ser montado");
                            System.exit(1);
                        }
                    }

                    for (i = 1; i < operandos.length; i++) {
                        
                        if (operandosMap.containsKey(macroOperandos.get(operandos[i]))) {
                            
                            pw.print(operandosMap.get(macroOperandos.get(operandos[i])) + " ");
                        } else {

                            if (tabelaDeUso.containsKey(macroOperandos.get(operandos[i]))) {

                                pw.print("00 ");
                            } else {

                                System.out.println("Erro! Operando " + (i + 1) + " na macro nÃ£o encontrado\nO cÃ³digo nÃ£o pode ser montado");
                                break;
                            }
                        }
                    }    
                }
                
                pw.print("\n");
                flag_operandos = 0;
                flag_nextline = 0;
            } 
        }
        
        
        catch (Exception e) {
            
        }
        
        return pw;
    }
}
