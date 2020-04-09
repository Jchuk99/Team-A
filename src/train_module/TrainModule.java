package src.train_module;

import src.Module;
import src.train_controller.TrainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TrainModule extends Module {

    int nextID = 1;
    ObservableList<Train> trainList;

    public TrainModule() {
        super();
        TrainModuleUI.setModule(this);
        trainList = FXCollections.observableArrayList();
    }

    public void main() {

    }

    @Override
    public void update() {
        for (Train train : trainList) {
            train.update();
            if (train.removeFlag) trainList.remove(train);
        }
    }

    public Train createTrain() {
        //create controller
        TrainController controller = trainControllerModule.createTrainController();
        Train train = new Train(nextID++, controller, trackModule.getYard());
        controller.attachTrain(train);
        trainList.add(train);
        return train;
    }

    // for GUI

    public ObservableList<Train> getTrainList() {
        return trainList;
    }

}