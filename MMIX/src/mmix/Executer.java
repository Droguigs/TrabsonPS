/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmix;

import executerUtilities.CodeLine;
import executerUtilities.DataMemory;
import java.util.HashMap;
 
/**
 *
 * @author bender
 */
public class Executer {
    
    private HashMap<Integer, Runnable> instructions;
    private DataMemory memoria;
    private long $LC;
    private CodeLine line;
    
    public Executer(){
        instructions = new HashMap<>();
        memoria = new DataMemory();
        
        // Insere as instrucoes na hash
        instructions.put(0X20, () -> ADD());        
        instructions.put(0X24, () -> SUB());        
        instructions.put(0XC0, () -> OR());        
        instructions.put(0XC8, () -> AND());        
        instructions.put(0XCC, () -> NAND());   
        instructions.put(0XC4, () -> NOR());
        instructions.put(0XC6, () -> XOR());
        instructions.put(0XCE, () -> NXOR());
        instructions.put(0X10, () -> MUL());
        instructions.put(0X14, () -> DIV());
        instructions.put(0X60, () -> CSN());
        instructions.put(0X62, () -> CSZ());
        instructions.put(0X64, () -> CSP());
        instructions.put(0X66, () -> CSOD());
        instructions.put(0X6E, () -> CSEV());
        instructions.put(0X6A, () -> CSNZ());
        instructions.put(0X68, () -> CSNN());
        instructions.put(0X6C, () -> CSNP());
        instructions.put(0X70, () -> ZSN());
        instructions.put(0X72, () -> ZSZ());
        instructions.put(0X74, () -> ZSP());
        instructions.put(0X76, () -> ZSOD());
        instructions.put(0X7E, () -> ZSEV());
        instructions.put(0X7A, () -> ZSNZ());
        instructions.put(0X78, () -> ZSNN());
        instructions.put(0X7C, () -> ZSNP());
        instructions.put(0X3C, () -> SR());
        instructions.put(0X38, () -> SL());
        instructions.put(0X80, () -> LDB());
        instructions.put(0X84, () -> LDW());
        instructions.put(0X88, () -> LDT());
        instructions.put(0X8C, () -> LDO());
        instructions.put(0XA0, () -> STB());
        instructions.put(0XA4, () -> STW());
        instructions.put(0XA8, () -> STT());
        instructions.put(0XAC, () -> STO());
        instructions.put(0X34, () -> NEG());
        instructions.put(0X30, () -> CMP());
        instructions.put(0X44, () -> BP());
        instructions.put(0X4A, () -> BNZ());
        instructions.put(0X48, () -> BNN());
        instructions.put(0X4C, () -> BNP());
        instructions.put(0X46, () -> BOD());
        instructions.put(0X4E, () -> BEV());
        instructions.put(0XF0, () -> JMP());
        
        instructions.put(0X21, () -> ADDI());
        instructions.put(0X25, () -> SUBI());
        instructions.put(0XC1, () -> ORI());
        instructions.put(0XC9, () -> ANDI());
        instructions.put(0XCD, () -> NANDI());
        instructions.put(0XC5, () -> NORI());
        instructions.put(0XC7, () -> XORI());
        instructions.put(0X11, () -> MULI());
        instructions.put(0X15, () -> DIVI());
        instructions.put(0X61, () -> CSNI());
        instructions.put(0X63, () -> CSZI());
        instructions.put(0X65, () -> CSPI());
        instructions.put(0X67, () -> CSODI());
        instructions.put(0X6F, () -> CSEVI());
        instructions.put(0X6B, () -> CSNZI());
        instructions.put(0X69, () -> CSNNI());
        instructions.put(0X6D, () -> CSNPI());
        instructions.put(0X71, () -> ZSNI());
        instructions.put(0X73, () -> ZSZI());
        instructions.put(0X75, () -> ZSPI());
        instructions.put(0X77, () -> ZSODI());
        instructions.put(0X7F, () -> ZSEVI());
        instructions.put(0X7B, () -> ZSNZI());
        instructions.put(0X79, () -> ZSNNI());
        instructions.put(0X7D, () -> ZSNPI());
        instructions.put(0X3D, () -> SRI());
        instructions.put(0X39, () -> SLI());        
        instructions.put(0X81, () -> LDBI());
        instructions.put(0X85, () -> LDWI());
        instructions.put(0X89, () -> LDTI());
        instructions.put(0X8D, () -> LDOI());
        instructions.put(0XA1, () -> STBI());
        instructions.put(0XA5, () -> STWI());
        instructions.put(0XA9, () -> STTI());
        instructions.put(0XAD, () -> STOI());
        instructions.put(0X35, () -> NEGI());
        instructions.put(0X31, () -> CMPI());
    
    }
    
    
    public void runCode(String filename){
        
    }
    
    public void incrementaLC(long inc){
        $LC = $LC + inc;
    }
    public long getLC(){
        return this.$LC;
    }

    private void execute(CodeLine line){
        this.line = line;
        instructions.get(line.getCode()).run();
    }
    
    // Instructions
    private void ADD(){
        memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) + memoria.readOcta(line.getZ() * 8));
        incrementaLC(3);
    }
    private void ADDI(){
        memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) + line.getZ());
        incrementaLC(3);
    }
    private void SUB(){
        memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) - memoria.readOcta(line.getZ() * 8));
        incrementaLC(3);
    }
    private void SUBI(){
        memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) - line.getZ());
        incrementaLC(3);
    }
    private void MUL(){
        memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) * memoria.readOcta(line.getZ() * 8));
        incrementaLC(3);
    }
    private void MULI(){
        memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) * line.getZ());
        incrementaLC(3);
    }
    private void DIV(){
        if(line.getZ() == 0)
           memoria.storeOcta(line.getX() * 8, 0);
        else   
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) * memoria.readOcta(line.getZ() * 8));
       
        incrementaLC(3);
    }
    private void DIVI(){
        if(line.getZ() == 0)
           memoria.storeOcta(line.getX() * 8, 0);
        else   
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) * line.getZ());
       
        incrementaLC(3);
    }
    
    private void OR(){
        memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) | memoria.readOcta(line.getZ() * 8));
        incrementaLC(3);
    }
    private void ORI(){
        memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) | line.getZ());
        incrementaLC(3);
    }
    private void AND(){
       memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) & memoria.readOcta(line.getZ() * 8));
       incrementaLC(3);
    }
    private void ANDI(){
       memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) & line.getZ());
       incrementaLC(3);
    }
    private void XOR(){
        memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) ^ memoria.readOcta(line.getZ() * 8));
        incrementaLC(3);
    }
    private void XORI(){
        memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) ^ line.getZ());
        incrementaLC(3);
    }
    private void NOR(){
        memoria.storeOcta(line.getX() * 8, ~(memoria.readOcta(line.getY() * 8) | memoria.readOcta(line.getZ() * 8)));
        incrementaLC(3);
    }
    private void NORI(){
        memoria.storeOcta(line.getX() * 8, ~(memoria.readOcta(line.getY() * 8) | line.getZ()));
        incrementaLC(3);
    }
    private void NAND(){
        memoria.storeOcta(line.getX() * 8, ~(memoria.readOcta(line.getY() * 8) & memoria.readOcta(line.getZ() * 8)));
        incrementaLC(3);
    }
    private void NANDI(){
        memoria.storeOcta(line.getX() * 8, ~(memoria.readOcta(line.getY() * 8) & line.getZ()));
        incrementaLC(3);
    }
    private void NXOR(){
        memoria.storeOcta(line.getX() * 8, ~(memoria.readOcta(line.getY() * 8) ^ memoria.readOcta(line.getZ() * 8)));
        incrementaLC(3);
    }
    private void NXORI(){
        memoria.storeOcta(line.getX() * 8, ~(memoria.readOcta(line.getY() * 8) ^ line.getZ()));
        incrementaLC(3);
    }
    
    private void CSN(){
        
        // Registrador x recebe o resultado
        if(memoria.readOcta(line.getY() * 8) < 0)
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getZ() * 8));
               
        incrementaLC(3);
        
    }
    private void CSNI(){
        
        // Registrador x recebe o resultado
        if(memoria.readOcta(line.getY() * 8) < 0)
            memoria.storeOcta(line.getX() * 8, line.getZ());
               
        incrementaLC(3);
        
    }
    private void CSZ(){
        // Registrador x recebe o resultado
        if(memoria.readOcta(line.getY() * 8) == 0)
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getZ() * 8));
               
        incrementaLC(3);
        
    }
    private void CSZI(){
        // Registrador x recebe o resultado
        if(memoria.readOcta(line.getY() * 8) == 0)
            memoria.storeOcta(line.getX() * 8, line.getZ());
               
        incrementaLC(3);
        
    }
    private void CSP(){
        // Registrador x recebe o resultado
        if(memoria.readOcta(line.getY() * 8) > 0)
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getZ() * 8));
               
        incrementaLC(3);
    }
    private void CSPI(){
        // Registrador x recebe o resultado
        if(memoria.readOcta(line.getY() * 8) > 0)
            memoria.storeOcta(line.getX() * 8, line.getZ());
               
        incrementaLC(3);
    }
    private void CSOD(){
        if(memoria.readOcta(line.getY()) * 2 == 1)
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getZ() * 8));
               
        incrementaLC(3);
        
    }
    private void CSODI(){
        if(memoria.readOcta(line.getY()) * 2 == 1)
            memoria.storeOcta(line.getX() * 8, line.getZ() * 8);
               
        incrementaLC(3);
        
    }
    private void CSEV(){
        if(memoria.readOcta(line.getY() * 8) % 2 == 0)
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getZ() * 8));
               
        incrementaLC(3);
    }
    private void CSEVI(){
        if(memoria.readOcta(line.getY() * 8) % 2 == 0)
            memoria.storeOcta(line.getX() * 8, line.getZ());
               
        incrementaLC(3);
    }
    private void CSNZ(){
        if(memoria.readOcta(line.getY() * 8) != 0)
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getZ() * 8));
               
        incrementaLC(3);
        
    }
    private void CSNZI(){
        if(memoria.readOcta(line.getY() * 8) != 0)
            memoria.storeOcta(line.getX() * 8, line.getZ());
               
        incrementaLC(3);
        
    }
    private void CSNN(){
        if(memoria.readOcta(line.getY() * 8) >= 0)
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getZ() * 8));
               
        incrementaLC(3);
    }
    private void CSNNI(){
        if(memoria.readOcta(line.getY() * 8) >= 0)
            memoria.storeOcta(line.getX() * 8, line.getZ());
               
        incrementaLC(3);
    }
    private void CSNP(){
        if(memoria.readOcta(line.getY() * 8) <= 0)
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getZ() * 8));
               
        incrementaLC(3);
    }
    private void CSNPI(){
        if(memoria.readOcta(line.getY() * 8) <= 0)
            memoria.storeOcta(line.getX() * 8, line.getZ() * 8);
               
        incrementaLC(3);
    }
    private void ZSN(){
        if(memoria.readOcta(line.getY() * 8) < 0)
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getZ() * 8));
        else
            memoria.storeOcta(line.getX() * 8, 0);
            
        incrementaLC(3);
    }
    private void ZSNI(){
        if(memoria.readOcta(line.getY() * 8) < 0)
            memoria.storeOcta(line.getX() * 8, line.getZ());
        else
            memoria.storeOcta(line.getX() * 8, 0);
            
        incrementaLC(3);
    }
    private void ZSZ(){
        if(memoria.readOcta(line.getY() * 8) == 0)
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getZ() * 8));
        else
            memoria.storeOcta(line.getX() * 8, 0);
            
        incrementaLC(3);
    }
    private void ZSZI(){
        if(memoria.readOcta(line.getY() * 8) == 0)
            memoria.storeOcta(line.getX() * 8, line.getZ());
        else
            memoria.storeOcta(line.getX() * 8, 0);
            
        incrementaLC(3);
    }
    private void ZSP(){
        if(memoria.readOcta(line.getY() * 8) > 0)
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getZ() * 8));
        else
            memoria.storeOcta(line.getX() * 8, 0);
            
        incrementaLC(3);
    }
    private void ZSPI(){
        if(memoria.readOcta(line.getY() * 8) > 0)
            memoria.storeOcta(line.getX() * 8, line.getZ());
        else
            memoria.storeOcta(line.getX() * 8, 0);
            
        incrementaLC(3);
    }
    private void ZSOD(){
        if(memoria.readOcta(line.getY() * 8) % 2 == 1)
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getZ() * 8));
        else
            memoria.storeOcta(line.getX() * 8, 0);
            
        incrementaLC(3);
    }
    private void ZSODI(){
        if(memoria.readOcta(line.getY() * 8) % 2 == 1)
            memoria.storeOcta(line.getX() * 8, line.getZ());
        else
            memoria.storeOcta(line.getX() * 8, 0);
            
        incrementaLC(3);
    }
    private void ZSEV(){
        if(memoria.readOcta(line.getY() * 8) % 2 == 0)
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getZ() * 8));
        else
            memoria.storeOcta(line.getX() * 8, 0);
            
        incrementaLC(3);
    }
    private void ZSEVI(){
        if(memoria.readOcta(line.getY() * 8) % 2 == 0)
            memoria.storeOcta(line.getX() * 8, line.getZ());
        else
            memoria.storeOcta(line.getX() * 8, 0);
            
        incrementaLC(3);
    }
    private void ZSNZ(){
        if(memoria.readOcta(line.getY() * 8) != 0)
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getZ() * 8));
        else
            memoria.storeOcta(line.getX() * 8, 0);
            
        incrementaLC(3);
    }
    private void ZSNZI(){
        if(memoria.readOcta(line.getY() * 8) != 0)
            memoria.storeOcta(line.getX() * 8, line.getZ());
        else
            memoria.storeOcta(line.getX() * 8, 0);
            
        incrementaLC(3);
    }
    private void ZSNN(){
        if(memoria.readOcta(line.getY() * 8) >= 0)
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getZ() * 8));
        else
            memoria.storeOcta(line.getX() * 8, 0);
            
        incrementaLC(3);
    }
    private void ZSNNI(){
        if(memoria.readOcta(line.getY() * 8) >= 0)
            memoria.storeOcta(line.getX() * 8, line.getZ());
        else
            memoria.storeOcta(line.getX() * 8, 0);
            
        incrementaLC(3);
    }
    private void ZSNP(){
        if(memoria.readOcta(line.getY() * 8) <= 0)
            memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getZ() * 8));
        else
            memoria.storeOcta(line.getX() * 8, 0);
            
        incrementaLC(3);
    }
    private void ZSNPI(){
        if(memoria.readOcta(line.getY() * 8) <= 0)
            memoria.storeOcta(line.getX() * 8, line.getZ());
        else
            memoria.storeOcta(line.getX() * 8, 0);
            
        incrementaLC(3);
    }
    
    
    private void SL(){
        memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) << memoria.readOcta(line.getZ() * 8));
        incrementaLC(3);
    }
    private void SLI(){
        memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) << line.getZ());
        incrementaLC(3);
    }
    private void SR(){
        memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) >> memoria.readOcta(line.getZ() * 8));
        incrementaLC(3);
    }
    private void SRI(){
        memoria.storeOcta(line.getX() * 8, memoria.readOcta(line.getY() * 8) >> line.getZ());
        incrementaLC(3);
    }
    
    // Loads and Stores
    private void LDB(){
        memoria.storeByte( memoria.readOcta( (line.getX() * 8)), memoria.readByte( (memoria.readOcta( line.getY() * 8) + memoria.readOcta(line.getZ() * 8))));
        incrementaLC(3);
    }
    private void LDBI(){
        memoria.storeByte(memoria.readOcta( (line.getX() * 8)), memoria.readByte( (memoria.readOcta( line.getY() * 8) +line.getZ())));
        incrementaLC(3);
    }
    private void LDW(){
        memoria.storeWyde(memoria.readOcta( (line.getX() * 8)), memoria.readWyde( (memoria.readOcta( line.getY() * 8) + memoria.readOcta(line.getZ() * 8))));
        incrementaLC(3);
    }
    private void LDWI(){
        memoria.storeWyde(memoria.readOcta( (line.getX() * 8)), memoria.readWyde( (memoria.readOcta( line.getY() * 8) + line.getZ())));
        incrementaLC(3);
    }
    private void LDT(){
        memoria.storeTetra(memoria.readOcta( (line.getX() * 8)), memoria.readTetra( (memoria.readOcta( line.getY() * 8) + memoria.readOcta(line.getZ() * 8))));
        incrementaLC(3);
    }
    private void LDTI(){
        memoria.storeTetra(memoria.readOcta( (line.getX() * 8)), memoria.readTetra( (memoria.readOcta( line.getY() * 8) + line.getZ())));
        incrementaLC(3);
    }
    private void LDO(){
        memoria.storeOcta(memoria.readOcta( (line.getX() * 8)), memoria.readOcta( (memoria.readOcta( line.getY() * 8) + memoria.readOcta(line.getZ() * 8))));
        incrementaLC(3);
    }
    private void LDOI(){
        memoria.storeOcta(memoria.readOcta( (line.getX() * 8)), memoria.readOcta( (memoria.readOcta( line.getY() * 8) + line.getZ())));
        incrementaLC(3);
    }
    
    private void STB(){
       memoria.storeByte((memoria.readOcta(line.getY() * 8) + memoria.readOcta(line.getZ() * 8)), memoria.readByte( line.getX() * 8));
       incrementaLC(3);
    }
    private void STBI(){
       memoria.storeByte((memoria.readOcta(line.getY() * 8) + line.getZ()), memoria.readByte( line.getX() * 8));
       incrementaLC(3);
    }
    private void STW(){
        memoria.storeWyde((memoria.readOcta(line.getY() * 8) + memoria.readOcta(line.getZ() * 8)), memoria.readWyde( line.getX() * 8));
        incrementaLC(3);
    }
    private void STWI(){
        memoria.storeWyde((memoria.readOcta(line.getY() * 8) + line.getZ()), memoria.readWyde( line.getX() * 8));
        incrementaLC(3);
    }
    private void STT(){
        memoria.storeTetra((memoria.readOcta(line.getY() * 8) + memoria.readOcta(line.getZ() * 8)), memoria.readTetra( line.getX() * 8));
        incrementaLC(3);
    }
    private void STTI(){
        memoria.storeTetra((memoria.readOcta(line.getY() * 8) + line.getZ()), memoria.readTetra( line.getX() * 8));
        incrementaLC(3);
    }
    private void STO(){
        memoria.storeOcta((memoria.readOcta(line.getY() * 8) + memoria.readOcta(line.getZ() * 8)), memoria.readOcta( line.getX() * 8));
        incrementaLC(3);
    }
    private void STOI(){
        memoria.storeOcta((memoria.readOcta(line.getY() * 8) + line.getZ()), memoria.readOcta( line.getX() * 8));
        incrementaLC(3);
    }
    
    private void NEG(){
        memoria.storeOcta(line.getX() * 8, line.getY() - memoria.readOcta(line.getZ() * 8));
        incrementaLC(3);
    }
    private void NEGI(){
        memoria.storeOcta(line.getX() * 8, line.getY() - line.getZ());
        incrementaLC(3);
    }
    
    private void CMP(){
        long result;
        
        if(memoria.readOcta(line.getY() * 8) < memoria.readOcta(line.getZ() * 8))
            result = -1;
        else if(memoria.readOcta(line.getY() * 8) == memoria.readOcta(line.getZ() * 8))
            result = 0;
        else // if(y > z)
            result = 1;   
        
        // Registrador x recebe o resultado
        memoria.storeOcta(line.getX() * 8, result);
        
        incrementaLC(3);
    }
    private void CMPI(){
        long result;
        
        if(memoria.readOcta(line.getY() * 8) < line.getZ())
            result = -1;
        else if(memoria.readOcta(line.getY() * 8) == line.getZ())
            result = 0;
        else // if(y > z)
            result = 1;   
        
        // Registrador x recebe o resultado
        memoria.storeOcta(line.getX() * 8, result);
        
        incrementaLC(3);
    }
    
    // Desvios
    // Nessas instruções, deve receber um valor imediato de acordo com o label (Num. PC nesse caso)
    private void BN(){
        // Caso a condição seja verdadeira
        if(memoria.readOcta( line.getX()) < 0)
            $LC = line.getY();        
    }
    private void BP(){
        // Caso a condição seja verdadeira
        if(memoria.readOcta( line.getX()) > 0)
            $LC = line.getY(); 
    }
    private void BZ(){
        // Caso a condição seja verdadeira
        if(memoria.readOcta( line.getX()) == 0)
            $LC = line.getY();   
    }
    private void BNZ(){
        // Caso a condição seja verdadeira
        if(memoria.readOcta( line.getX()) != 0)
            $LC = line.getY();         
    }
    private void BNN(){
        // Caso a condição seja verdadeira
        if(memoria.readOcta( line.getX()) >= 0)
            $LC = line.getY();     
    }
    private void BNP(){
       // Caso a condição seja verdadeira
        if(memoria.readOcta( line.getX()) <= 0)
            $LC = line.getY();     
    }
    private void BOD(){
       // Caso a condição seja verdadeira
        if(memoria.readOcta( line.getX()) % 2 == 1)
            $LC = line.getY();      
    }
    private void BEV(){
        // Caso a condição seja verdadeira
        if(memoria.readOcta( line.getX()) % 2 == 0)
            $LC = line.getY();      
    }
    
    private void JMP(){
        $LC = line.getY(); 
    }
}
