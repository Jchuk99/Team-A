package src.train_module;

import src.Module;
import src.train_controller.TrainController;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TrainModule extends Module {

    int nextID = 1;
    ObservableList<Train> trainList;
    ArrayList<Train> removeList;

    public TrainModule() {
        super();
        TrainModuleUI.setModule(this);
        trainList = FXCollections.observableArrayList();
    }

    public void main() {

    }

    @Override
    public void update() {
        removeList = new ArrayList<Train>();
        for (Train train : trainList) {
            train.update();
            if (train.removeFlag.getValue()) removeList.add(train);
        }
        for (Train train : removeList) {
            trainList.remove(train);
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