package src.ctc;

import javafx.application.Application;

public class CTCMain{

  /*  public static void main(String [] args){
        Application.launch(CTCUI.class, args);
    }*/

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                Application.launch(CTCUI.class);
            }
        }.start();
        CTCUI ctcUI = CTCUI.waitForStartUpTest();
       
        CTCModule ctcOffice = new CTCModule();
        ctcUI.setCTCModule(ctcOffice);
   
    }  
} 