package src.train_module;

import src.Module;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TrainModule extends Module {

   int nextID = 0;
   ObservableList<Train> trainList;

   public TrainModule() {
      trainList = FXCollections.observableArrayList();
   }

   public void main() {

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


   // for GUI

   public ObservableList<Train> getTrainList() {
      return trainList;
   }

   // private methods

   private Train getTrain(int id) {
      for (Train train : trainList) {
         if (train.UUID == id) return train;
      }
      return null;
   }

}