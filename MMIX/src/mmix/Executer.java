/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmix;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 *
 * @author bender
 */
public class Executer {
    
    private HashMap<Integer, Supplier<Register>> hash;
    
    // Registradores auxiliares para executar as operacoes
    private Register x;
    private Register y;
    private Register z;
    
    public Executer(){
        hash = new HashMap<>();
        x = new Register("x");
        y = new Register("y");
        z = new Register("z");
        
        hash.put(0, () -> ADD());
        // Insere as instrucoes na hash
        
    }
    
    public Register execute(CodeLine line){
        
        this.x = line.getX();
        this.y = line.getY();
        this.z = line.getZ();
        return hash.get(line.getCode()).getAsLong(); 
    }
    
    
    
    private Register ADD(){
        Register newReg = new Register(""){
            
        }
        
        return this.y.getContent() + this.z.getContent();        
    }
    
}
