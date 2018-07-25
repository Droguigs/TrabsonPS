/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmix;

import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 *
 * @author bender
 */
public class Executer {
    
    HashMap<Integer, Callable> hash;
    Register x;
    Register y;
    Register z;
    
    public Executer(){
        hash = new HashMap<>();
        x = new Register("x");
        y = new Register("y");
        z = new Register("z");
        
        hash.put(0, ADD());
        // Insere as instrucoes na hash
        
    }
    
    
    
    private Long ADD(){
        return this.y.getContent() + this.z.getContent();        
    }
    
}
