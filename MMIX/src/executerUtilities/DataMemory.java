/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package executerUtilities;

import java.util.ArrayList;

/**
 *
 * @author snore
 */
public class DataMemory {
    private ArrayList<Byte> memArray;
    
    public DataMemory(){
        memArray = new ArrayList();
        for(int i = 0; i < 262144; i++)
            memArray.add(i, (byte)0);
        
    }
    
    public long readByte(long byteIndex){
        long aux = memArray.get((int)byteIndex);
        
        if(aux < 0)
            aux = aux & 255;
        
        return aux;
    }
    public long readWyde(long byteIndex){
       long ret = 0, aux;
       int index = ((int)byteIndex/2) * 2;     // Deve ser divisível por 2
       
       // MMIX is Big Endian
       for(int i=1; i>=0; i--, index++){
           aux = memArray.get(index);
           if(aux < 0)
               aux = aux & 255;           
           
           aux = aux << (8 * i);
           ret = ret | aux;
       }

       return ret;

    }
    public long readTetra(long byteIndex){
        long ret = 0, aux; 
        int index = ((int)byteIndex/4) * 4;     // Deve ser divisível por 4
        
        // MMIX is Big Endian
        for(int i=3; i>=0; i--, index++){
            aux = memArray.get(index);
            if(aux < 0)
               aux = aux & 255;
            
            aux = aux << (8 * i);
            ret = ret | aux;
        }
        
        return ret;
        
    }
    public long readOcta(long byteIndex){
        long ret = 0, aux; 
        int index = ((int)byteIndex/8) * 8;     // Deve ser divisível por 8
        
        // MMIX is Big Endian
        for(int i=7; i>=0; i--, index++){
            aux = memArray.get(index);
            if(aux < 0)
               aux = aux & 255;
            
            aux = aux << (8 * i);
            ret = ret | aux;
        }
        
        return ret;
        
    }
    
    public void storeByte(long byteIndex, long value){
        byte v = (byte)value;
        memArray.set((int)byteIndex, v);
    }
    public void storeWyde(long byteIndex, long value){
       int index = ((int)byteIndex / 2 * 2) + 1;
       byte v;
       
       // MMIX is Big Endian
       for(int i=0; i<2; i++, index--){
           v = (byte)value;
           value = value >> 8;
           memArray.set(index, v);
       }

    }
    public void storeTetra(long byteIndex, long value){
        
        int index = ((int)byteIndex / 4 * 4) + 3;
        byte v;
       
       // MMIX is Big Endian
       for(int i=0; i<4; i++, index--){
           v = (byte)value;
           value = value >> 8;
           memArray.set(index, v);
       }
        
    }
    public void storeOcta(long byteIndex, long value){
        
        int index = ((int)byteIndex / 8 * 8) + 7;
        byte v;
       
       // MMIX is Big Endian
       for(int i=0; i<8; i++, index--){
           v = (byte)value;
           value = value >> 8;
           memArray.set(index, v);
       }
        
    }
    
    /** Retorna a memoria inteira 
     * Esse método deve ser chamado apenas pela interface gráfica para plotar a memória
     * 
     * @return A memoria de dados
     */
    public ArrayList getMemory(){
        return memArray;
    }
    
}
