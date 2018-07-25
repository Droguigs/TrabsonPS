/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmix;

/**
 *
 * @author bender
 */
public class MMIX {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DataMemory memoria = new DataMemory();          // Memoria de dados
        CodeLine[] executavel = new CodeLine[8000];     // Linhas de instrucao
        Executer exec = new Executer();
        
        
        Register x = new Register("x");
        Register y = new Register("y");
        Register z = new Register("z");
        y.setContent((long)45);
        z.setContent((long)25);
        executavel[0] = new CodeLine(0, x, y, z);
        
        long value = exec.execute(executavel[0]);
        System.out.println("ADD: " + value);
        
        // Registradores
        Register PC = new Register("PC");
        
    }
    
}
