/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmix;

import executerUtilities.CodeLine;
import executerUtilities.DataMemory;
import executerUtilities.Register;
import java.util.HashMap;

/**
 *
 * @author bender
 */
public class Executer {
    
    private HashMap<Integer, Runnable> instructions;
    private DataMemory memoria;
    private Register[] generalRegisters;    // Banco de registradores de uso geral
    private Register $PC;
    private CodeLine line;
    private int pontoMontagem;
    
    public Executer(int montagem){
        instructions = new HashMap<>();
        memoria = new DataMemory();
        generalRegisters = new Register[256];
        $PC = new Register();
        pontoMontagem = montagem;
        
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
    }
    
    
    public void runCode(CodeLine[] codigo){
        
    }
    
    public Register getRegister(int index){
        return generalRegisters[index];
    }
    
    public Register[] getRegisters(){
        return generalRegisters;
    }
    
    public Register getPC(){
        return this.$PC;
    }

    private void execute(CodeLine line){
        this.line = line;
        instructions.get(line.getCode()).run();
    }
    

    private void incrementaPC(){
        $PC.setContent($PC.getContent() + 4);
    }
    private void jump(long offset){
        $PC.setContent(offset * 4);
    }
    
    // Instructions
    private void ADD(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Registrador x recebe o resultado
        generalRegisters[line.getX().getIndex()].setContent(y + z);
        
        incrementaPC();
    }
    private void SUB(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Registrador x recebe o resultado
        generalRegisters[line.getX().getIndex()].setContent(y - z);
        
        incrementaPC();
    }
    private void MUL(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Registrador x recebe o resultado
        generalRegisters[line.getX().getIndex()].setContent(y * z);
        
        incrementaPC();
    }
    private void DIV(){
        long y, z, zero = 0;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Registrador x recebe o resultado
        if(z == 0)
            generalRegisters[line.getX().getIndex()].setContent(zero);
        else
            generalRegisters[line.getX().getIndex()].setContent(y / z);
        
        
        incrementaPC();
    }
    
    private void OR(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Registrador x recebe o resultado
        generalRegisters[line.getX().getIndex()].setContent(y | z);
        
        incrementaPC();
    }
    private void AND(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Registrador x recebe o resultado
        generalRegisters[line.getX().getIndex()].setContent(y & z);
        
        incrementaPC();
    }
    private void XOR(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Registrador x recebe o resultado
        generalRegisters[line.getX().getIndex()].setContent(y ^ z);
        
        incrementaPC();
    }
    private void NOR(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Registrador x recebe o resultado
        generalRegisters[line.getX().getIndex()].setContent(~(y | z));
        
        incrementaPC();
    }
    private void NAND(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Registrador x recebe o resultado
        generalRegisters[line.getX().getIndex()].setContent(~(y & z));
        
        incrementaPC();
    }
    private void NXOR(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Registrador x recebe o resultado
        generalRegisters[line.getX().getIndex()].setContent(~(y ^ z));
        
        incrementaPC();
    }
    
    private void CSN(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y < 0){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        
        incrementaPC();
        
    }
    private void CSZ(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y == 0){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        
        incrementaPC();
        
    }
    private void CSP(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y > 0){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        
        incrementaPC();
    }
    private void CSOD(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y % 2 == 1){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        incrementaPC();
        
    }
    private void CSEV(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y % 2 == 0){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        incrementaPC();
    }
    private void CSNZ(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y != 0){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        
        incrementaPC();
    }
    private void CSNN(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y >= 0){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        
        incrementaPC();
    }
    private void CSNP(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y <= 0){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        
        incrementaPC();
    }
    
    private void ZSN(){
        long y, z, zero = 0;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y < 0){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        else
            generalRegisters[line.getX().getIndex()].setContent(zero);
        
        incrementaPC();
    }
    private void ZSZ(){
        long y, z, zero = 0;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y == 0){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        else
            generalRegisters[line.getX().getIndex()].setContent(zero);
        
        incrementaPC();
    }
    private void ZSP(){
        long y, z, zero = 0;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y > 0){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        else
            generalRegisters[line.getX().getIndex()].setContent(zero);
        
        incrementaPC();
    }
    private void ZSOD(){
        long y, z, zero = 0;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y % 2 == 1){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        else
            generalRegisters[line.getX().getIndex()].setContent(zero);
        
        incrementaPC();
    }
    private void ZSEV(){
        long y, z, zero = 0;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y % 2 == 0){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        else
            generalRegisters[line.getX().getIndex()].setContent(zero);
        
        incrementaPC();
    }
    private void ZSNZ(){
        long y, z, zero = 0;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y != 0){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        else
            generalRegisters[line.getX().getIndex()].setContent(zero);
        
        incrementaPC();
    }
    private void ZSNN(){
        long y, z, zero = 0;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y >= 0){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        else
            generalRegisters[line.getX().getIndex()].setContent(zero);
        
        incrementaPC();
    }
    private void ZSNP(){
        long y, z, zero = 0;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y <= 0){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        else
            generalRegisters[line.getX().getIndex()].setContent(zero);
        
        incrementaPC();
    }
    
    private void SL(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y <= 0){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        else
            generalRegisters[line.getX().getIndex()].setContent(y << z);
        
        incrementaPC();
    }
    private void SR(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y <= 0){
             // Registrador x recebe o resultado
             generalRegisters[line.getX().getIndex()].setContent(z);
        }
        else
            generalRegisters[line.getX().getIndex()].setContent(y >> z);
        
        incrementaPC();
    }
    
    // Loads and Stores
    private void LDB(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Registrador x recebe o resultado
        generalRegisters[line.getX().getIndex()].setContent(memoria.readByte((int)(y+z + pontoMontagem)));
        
        incrementaPC();
    }
    private void LDW(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Registrador x recebe o resultado
        generalRegisters[line.getX().getIndex()].setContent(memoria.readWyde((int)(y+z + pontoMontagem)));
        
        incrementaPC();
    }
    private void LDT(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Registrador x recebe o resultado
        generalRegisters[line.getX().getIndex()].setContent(memoria.readTetra((int)(y+z + pontoMontagem)));
        
        incrementaPC();
    }
    private void LDO(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Registrador x recebe o resultado
        generalRegisters[line.getX().getIndex()].setContent(memoria.readOcta((int)(y+z + pontoMontagem)));
        
        incrementaPC();
    }
    
    private void STB(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Memoria recebe o valor de X
        memoria.storeByte(pontoMontagem + (int)(y + z), generalRegisters[line.getX().getIndex()].getContent());
        
        incrementaPC();
    }
    private void STW(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Memoria recebe o valor de X
        memoria.storeWyde(pontoMontagem + (int)(y + z), generalRegisters[line.getX().getIndex()].getContent());
        
        incrementaPC();
    }
    private void STT(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Memoria recebe o valor de X
        memoria.storeTetra(pontoMontagem + (int)(y + z), generalRegisters[line.getX().getIndex()].getContent());
        
        incrementaPC();
    }
    private void STO(){
        long y, z;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Memoria recebe o valor de X
        memoria.storeOcta(pontoMontagem + (int)(y + z), generalRegisters[line.getX().getIndex()].getContent());
        
        incrementaPC();
    }

    private void NEG(){
        long y, z;
        
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y sempre é immediate
        y = line.getY().getImmediate();
        
        // Registrador X recebe o resultado
        generalRegisters[line.getX().getIndex()].setContent(y - z);
        
        incrementaPC();        
    }
    
    private void CMP(){
        long y, z, result;
        
        // Operador z recebe o valor desejado (pode ser imediato)
        if(line.getZ().isImmediate())   z = line.getZ().getImmediate();
        else                            z = generalRegisters[line.getZ().getIndex()].getContent();
        
        // Operador y recebe seu valor desejado (Indice para registrador)
        y = generalRegisters[line.getZ().getIndex()].getContent();
        
        if(y < z)
            result = -1;
        else if(y == z)
            result = 0;
        else // if(y > z)
            result = 1;   
        
        // Registrador x recebe o resultado
        generalRegisters[line.getX().getIndex()].setContent(result);
        
        incrementaPC();
    }
    
    // Desvios
    // Nessas instruções, deve receber um valor imediato de acordo com o label (Num. PC nesse caso)
    private void BN(){
        long y;
        
        // y é sempre imediato, não há z
        y = line.getY().getImmediate();
        
        // Caso a condição seja verdadeira
        if(generalRegisters[line.getX().getIndex()].getContent() < 0)
            jump(y);        
    }
    private void BP(){
        long y;
        
        // y é sempre imediato, não há z
        y = line.getY().getImmediate();
        
        // Caso a condição seja verdadeira
        if(generalRegisters[line.getX().getIndex()].getContent() > 0)
            jump(y);        
    }
    private void BZ(){
        long y;
        
        // y é sempre imediato, não há z
        y = line.getY().getImmediate();
        
        // Caso a condição seja verdadeira
        if(generalRegisters[line.getX().getIndex()].getContent() == 0)
            jump(y);        
    }
    private void BNZ(){
        long y;
        
        // y é sempre imediato, não há z
        y = line.getY().getImmediate();
        
        // Caso a condição seja verdadeira
        if(generalRegisters[line.getX().getIndex()].getContent() != 0)
            jump(y);        
    }
    private void BNN(){
        long y;
        
        // y é sempre imediato, não há z
        y = line.getY().getImmediate();
        
        // Caso a condição seja verdadeira
        if(generalRegisters[line.getX().getIndex()].getContent() >= 0)
            jump(y);        
    }
    private void BNP(){
        long y;
        
        // y é sempre imediato, não há z
        y = line.getY().getImmediate();
        
        // Caso a condição seja verdadeira
        if(generalRegisters[line.getX().getIndex()].getContent() <= 0)
            jump(y);        
    }
    private void BOD(){
        long y;
        
        // y é sempre imediato, não há z
        y = line.getY().getImmediate();
        
        // Caso a condição seja verdadeira
        if(generalRegisters[line.getX().getIndex()].getContent() % 2 == 1)
            jump(y);        
    }
    private void BEV(){
        long y;
        
        // y é sempre imediato, não há z
        y = line.getY().getImmediate();
        
        // Caso a condição seja verdadeira
        if(generalRegisters[line.getX().getIndex()].getContent() % 2 == 0)
            jump(y);        
    }
    
    private void JMP(){
        long x = line.getX().getImmediate();
        jump(x);
        
    }
}
