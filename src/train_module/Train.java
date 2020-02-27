package src.train_module;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Train {

   int UUID;
   int currentBlock = -1;
   float currentSpeed = 0;
   float currentPower = 0;
   float currentPosition = 0;
   float currentAcceleration = 0;
   float temperatureInside = 70;

   float suggestedSpeed = 0;
   float authority = 0;
   float targetPower = 0;

   int passengerCount = 10;
   float weight = (float)52.2;

   Boolean leftDoorWorking = true;
   Boolean rightDoorWorking = true;
   Boolean lightWorking = true;
   Boolean serviceBrakeWorking = true;
   Boolean emergencyBrakeWorking = true;
   Boolean engineWorking = true;

   final static float emptyWeight = 47;

   public Train(int id) {
      UUID = id;
      // TODO: create train controller with UUID
      // trainControllerModule.createTrainController(id, this);
   }

   public StringProperty getName() {
      return new SimpleStringProperty("Train " + UUID);
   }

   public void update() {
      // TODO: put train id in correct block
   }

   public void setBlock() {
      // setBlock called by Yard

   }

   public void setTrain(float suggestedSpeed, float authority) {
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

   // TODO: add rest of the methods
}