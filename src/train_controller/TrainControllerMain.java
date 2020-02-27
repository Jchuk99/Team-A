package src.train_controller;

import src.train_controller.TrainControllerModule.TrainController;
import src.train_module.TrainModule;

public class TrainControllerMain {
	public static void main(String[] args){
		new Thread(){
			@Override
			public void run(){
				javafx.application.Application.launch(TrainControllerUI.class);
			}
		}.start();
		TrainModule t=new TrainModule();
		TrainControllerModule TCM=new TrainControllerModule();
		TrainControllerUI tcUI=TrainControllerUI.waitForStartUpTest();
		TrainController tc=TCM.createTrainController(t);
		tcUI.setTC(tc);
		
	}
}