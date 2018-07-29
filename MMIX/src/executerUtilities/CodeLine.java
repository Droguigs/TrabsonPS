/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package executerUtilities;


/**
 *
 * @author bender
 */
public class CodeLine {
    private Integer code;
    private long $X;
    private long $Y;
    private long $Z;

    public CodeLine(Integer code, Long x, Long y, Long z) {
        this.code = code;
        this.$X = x;
        this.$Y = y;
        this.$Z = z;
    }
    
    public int getCode(){
        return code;
    }
    
    public Long getX(){
        return $X;
    }
    
    public Long getY(){
        return $Y;
    }
    
    public Long getZ(){
        return $Z;
    }
    
   
}
