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
    private Parametro $X;
    private Parametro $Y;
    private Parametro $Z;

    public CodeLine(Integer code, Parametro x, Parametro y, Parametro z) {
        this.code = code;
        this.$X = x;
        this.$Y = y;
        this.$Z = z;
    }
    
    
    public int getCode(){
        return code;
    }
    
    public Parametro getX(){
        return $X;
    }
    
    public Parametro getY(){
        return $Y;
    }
    
    public Parametro getZ(){
        return $Z;
    }
    
   
}
