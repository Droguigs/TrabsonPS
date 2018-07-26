/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmix;

/**
 *
 * @author snore
 */
public class DataMemory {
    private byte[] memArray;
    
    public DataMemory(){
        memArray = new byte[102400];
    }
    
    public long readByte(int byteIndex){
        long aux = memArray[byteIndex];
        
        if(aux < 0)
            aux = aux & 255;
        
        return aux;
    }
    public long readWyde(int byteIndex){
       long ret = 0, aux;
       int index = (byteIndex/2) * 2;     // Deve ser divisível por 2
       
       // MMIX is Big Endian
       for(int i=1; i>=0; i--, index++){
           aux = memArray[index];
           if(aux < 0)
               aux = aux & 255;           
           
           aux = aux << (8 * i);
           ret = ret | aux;
       }

       return ret;

    }
    public long readTetra(int byteIndex){
        long ret = 0, aux; 
        int index = (byteIndex/4) * 4;     // Deve ser divisível por 4
        
        // MMIX is Big Endian
        for(int i=3; i>=0; i--, index++){
            aux = memArray[index];
            if(aux < 0)
               aux = aux & 255;
            
            aux = aux << (8 * i);
            ret = ret | aux;
        }
        
        return ret;
        
    }
    public long readOcta(int byteIndex){
        long ret = 0, aux; 
        int index = (byteIndex/8) * 8;     // Deve ser divisível por 8
        
        // MMIX is Big Endian
        for(int i=7; i>=0; i--, index++){
            aux = memArray[index];
            if(aux < 0)
               aux = aux & 255;
            
            aux = aux << (8 * i);
            ret = ret | aux;
        }
        
        return ret;
        
    }
    
    public void storeByte(int byteIndex, long value){
        byte v = (byte)value;
        memArray[byteIndex] = v;
    }
    public void storeWyde(int byteIndex, long value){
       int index = (byteIndex / 2 * 2) + 1;
       byte v;
       
       // MMIX is Big Endian
       for(int i=0; i<2; i++, index--){
           v = (byte)value;
           value = value >> 8;
           memArray[index] = v;
       }

    }
    public void storeTetra(int byteIndex, long value){
        
        int index = (byteIndex / 4 * 4) + 3;
        byte v;
       
       // MMIX is Big Endian
       for(int i=0; i<4; i++, index--){
           v = (byte)value;
           value = value >> 8;
           memArray[index] = v;
       }
        
    }
    public void storeOcta(int byteIndex, long value){
        
        int index = byteIndex + 7;
        byte v;
       
       // MMIX is Big Endian
       for(int i=0; i<8; i++, index--){
           v = (byte)value;
           value = value >> 8;
           memArray[index] = v;
       }
        
    }
    
}
