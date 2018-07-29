/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmix;

import executerUtilities.DataMemory;
import java.util.ArrayList;


/**
 *
 * @author bender
 */
public class MMIX {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Executer e = new Executer();
        ArrayList<Byte> lista;
        DataMemory mem = new DataMemory();
   
        e.runCode("testeExecuter.txt");
        mem.setMemory(e.getMem());

        System.out.println("" + mem.readOcta(8));
        
    }
    
}
