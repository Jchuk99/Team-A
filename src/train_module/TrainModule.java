package src.train_module;

import src.Module;
import src.track_module.Block;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TrainModule extends Module {

   ObservableList<Train> trainList;

   public TrainModule() {
      super();
      TrainModuleUI.setModule(this);
      trainList = FXCollections.observableArrayList();
   }

   public void main() {

   }

   public void update() {
      for (Train train : trainList) {
         train.update();
      }
   }

   public void createTrain(int id, Block block) {
      Train train = new Train(id, block);
      trainList.add(train);
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