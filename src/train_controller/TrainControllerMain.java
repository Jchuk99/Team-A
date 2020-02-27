package src.train_controller;

public class TrainControllerMain{
	public static void main(String[] args){
		new Thread(){
			@Override
			public void run(){
				javafx.application.Application.launch(TrainControllerUI.class);
			}
		}.start();
		TrainControllerUI tcUI=TrainControllerUI.waitForStartUpTest();
		TrainControllerModule tc=new TrainControllerModule();
		tcUI.setTC(tc);
		
	}
}