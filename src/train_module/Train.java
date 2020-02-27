package src.train_module;

import src.track_module.Block;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Train {

   int UUID;
   Block currentBlock;
   Boolean goForward = true;
   float currentSpeed = 0;
   float currentPower = 0;
   float currentPosition = 0;
   float currentAcceleration = 0;
   float temperatureInside = 70;

   float suggestedSpeed = 0;
   float authority = 0;
   float targetPower = 0;

   int passengerCount = 10;
   float currentWeight = (float)52.2;

   Boolean leftDoorWorking = true;
   Boolean rightDoorWorking = true;
   Boolean lightWorking = true;
   Boolean serviceBrakeWorking = true;
   Boolean emergencyBrakeWorking = true;
   Boolean engineWorking = true;

   final static float emptyWeight = 47;
   final static float length = 100;

   public Train(int id, Block block) {
      UUID = id;
      currentBlock = block;
      // TODO: check direction

      // TODO: create train controller with UUID
      // trainControllerModule.createTrainController(id, this);
   }

   public void update() {
      // TODO: put train id in correct block
   }

   public void setTrainProperties(float suggestedSpeed, float authority) {
      // setTrain called by track model only
      // TODO: pass data to train controller
      // trainController.setTrain(UUID, suggestedSpeed, authority);
   }

   public void setPower(float power) {
      // TODO: check max power
      targetPower = power;
   }

   public void destroyTrain() {
      // TODO: destroy train controller
   }

   // for GUI

   public StringProperty getName() {
      return new SimpleStringProperty("Train " + UUID);
   }

   // private methods

   private void setBlock() {

   }

}