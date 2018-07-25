package Controller;

import java.util.*;

public class ProcessadorDeMacros {

    private Macros macros;
    private ArrayList<String> cmdSequence;

    public ProcessadorDeMacros(String text){
        for (Macro macro: macros.macros) {
            if (text.equals(macro.getName())){
                cmdSequence = macro.cmdSequenceArray();
            }
        }
    }

}
