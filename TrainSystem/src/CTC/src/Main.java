import javafx.application.Application;

public class Main{

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
       
        CTCModule ctcOffice = new CTCModule(30);
        ctcUI.setCTCModule(ctcOffice);
        ctcOffice.setTest(20);
        ctcUI.printSomething();

}
}