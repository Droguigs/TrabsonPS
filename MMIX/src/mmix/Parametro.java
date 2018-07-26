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
public class Parametro {
    private Long immediate;
    private Integer index;
    
    public Parametro(long value){
        immediate = value;
        index = null;
    }
    
    public Parametro(int value){
        index = value;
        immediate = null;
    }
    
    public boolean isImmediate(){
        return (index == null);
    }
    
    public long getImmediate(){
        return immediate;
    }
    
    public int getIndex(){
        return index;
    }
    
}
