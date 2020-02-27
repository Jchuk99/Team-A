package src.train_module;

public class Train {

   int UUID;
   float currentSpeed = 0;
   float currentPower = 0;
   float currentPosition = 0;

   public Train(int id) {
      UUID = id;
      // TODO: create train controller with UUID
      // trainControllerModule.createTrainController(id, this);
   }

   public void update() {
      // TODO: put train id in correct block
   }

   public void setTrain(float suggestedSpeed, float authority) {
      // setTrain called by track model only
      // TODO: pass data to train controller
      // trainController.setTrain(UUID, suggestedSpeed, authority);
   }

   public void setPower(float power) {
      // TODO: check max power
      currentPower = power;
   }

   public void destroyTrain() {
      // TODO: destroy train controller
   }

   // TODO: add rest of the methods
}