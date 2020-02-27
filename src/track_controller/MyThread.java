package src.track_controller;

public class MyThread extends Thread { 
    public void run() 
    { 
        System.out.println("Current thread name: "
                           + Thread.currentThread().getName()); 
  
        System.out.println("run() method called"); 
    } 
} 