package src.ctc;

import javafx.application.Application;

public class CTCMain{

  /*  public static void main(String [] args){
        Application.launch(CTCUI.class, args);
    }*/

    public static void main(String[] args) {
        CTCModule ctcOffice = new CTCModule();
        CTCUI.setCTCModule(ctcOffice);
       // CTCUI ctcUI = CTCUI.waitForStartUpTest();
       
   
    }  
} 