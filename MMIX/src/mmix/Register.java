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
public class Register {
    private long content;
    
    public Register(){
        this.content = new Long(0);
    }
    
    public long getContent(){
        return this.content;
    }
    
    public void setContent(Long value){
        this.content = value;
    }
    
}