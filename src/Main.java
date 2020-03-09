package src;

import javafx.application.Application;


class Main {
    public static void main(String args[]) 
    {

        Thread thread = new Thread() {
            @Override
            public void run() {
                Application.launch(ApplicationUI.class);
            }
        };
        thread.start();

    }

} 