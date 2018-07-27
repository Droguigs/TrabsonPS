package Controller;

import java.util.*;

public class Macro {

    private String name;
    private int tag;
    private String cmdSequence; //Separados por ,

    public Macro(String name, int tag, String cmdSequence){
        this.name = name;
        this.tag = tag;
        this.cmdSequence = cmdSequence;
    }

    ArrayList<String> cmdSequenceArray(){
        ArrayList<String> myList = new ArrayList<String>(Arrays.asList(cmdSequence.split(",")));

        return myList;
    }

    int getTag(){
        return tag;
    }

    String getName(){
        return name;
    }

}
