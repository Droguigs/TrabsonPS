/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmix;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 *
 * @author bender
 */
public class Executer {
    
    private HashMap<Integer, Runnable> instructions;
    private DataMemory memoria;
    Register[] generalRegisters;    // Banco de registradores de uso geral
    Register $PC;
    CodeLine line;
    int pontoMontagem;
    
    public Executer(int montagem){
        instructions = new HashMap<>();
        memoria = new DataMemory();
        generalRegisters = new Register[256];
        $PC = new Register();
        pontoMontagem = montagem;
        
        // Insere as instrucoes na hash
        instructions.put(45, () -> ADD());
        
    }
    
    private void execute(CodeLine line){
        this.line = line;
        instructions.get(line.getCode()).run();
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
    private void MULT(){
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
    
    private void SDB(){
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
    private void SDW(){
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
    private void SDT(){
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
    private void SDO(){
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
