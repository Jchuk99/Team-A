package src.train_module;

import src.Module;
import java.util.ArrayList;

public class TrainModule extends Module {

   int nextID = 0;
   ArrayList<Train> trainList;

   public TrainModule() {
      
   }

   public void update() {
      for (Train train : trainList) {
         train.update();
      }
   }

   public void setTrain(int id, float suggestedSpeed, float authority) {
      Train train = getTrain(id);
      if (train != null) train.setTrain(suggestedSpeed, authority);
   }

   public void setPower(int id, float power) {
      Train train = getTrain(id);
      if (train != null) train.setPower(power);
   }

   public int createTrain() {
      Train train = new Train(++nextID);
      trainList.add(train);
      return train.UUID;
   }

   public void destroyTrain(int id) {
      Train train = getTrain(id);
      if (train != null) train.destroyTrain();
   }



   // private methods

   private Train getTrain(int id) {
      for (Train train : trainList) {
         if (train.UUID == id) return train;
      }
      return null;
   }

}