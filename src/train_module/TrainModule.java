package src.train_module;

import src.Module;
import src.track_module.Block;
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
        }
    }

    public Train createTrain() {
        //create controller
        TrainController controller = trainControllerModule.createTrainController();
        Train train = new Train(nextID++, controller);
        controller.attachTrain(train);
        trainList.add(train);
        return train;
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