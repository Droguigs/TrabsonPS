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
public class CodeLine {
    private Integer code;
    private Register $X;
    private Register $Y;
    private Register $Z;

    public CodeLine(Integer code, Register x, Register y, Register z) {
        this.code = code;
        this.$X = x;
        this.$Y = y;
        this.$Z = z;
    }
    
    public int getCode(){
        return code;
    }
    
    public Register getX(){
        return $X;
    }
    
    public Register getY(){
        return $Y;
    }
    
    public Register getZ(){
        return $Z;
    }
    
    
   
}
