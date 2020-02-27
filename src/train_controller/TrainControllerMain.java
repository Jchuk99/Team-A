

public class Main{
	public static void main(String[] args){
		new Thread(){
			@Override
			public void run(){
				javafx.application.Application.launch(TrainControllerUI.class);
			}
		}.start();
		TrainControllerUI tcUI=TrainControllerUI.waitForStartUpTest();
		TrainController tc=new TrainController();
		tcUI.setTC(tc);
		
	}
}