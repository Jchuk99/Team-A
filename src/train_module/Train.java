package src.train_module;

import src.Module;
import src.track_module.Block;
import src.track_module.BlockConstructor.*;
import src.track_module.Edge;
import src.train_controller.TrainController;
import src.UICommon;
import src.CrashUI;
import java.util.Random;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;

public class Train {
    
    public BooleanProperty removeFlag = new SimpleBooleanProperty(false);
    public boolean crashed = false;

    /****** constants ******/
    // in tons
    public final static float emptyWeight = (float) 40.9;
    public final static float passengerWeight = (float) 0.07;
    // in meter
    public final static float length = (float) 32.2;
    // in kW
    public final static float maxPower = (float) 480;
    // in kN
    public final static float maxForce = (float) 480;
    public final static float serviceBrakeForce = (float) 61.7;
    public final static float emergencyBrakeForce = (float) 486;
    // in m/s^2
    public final static float gravity = (float) 9.81;
    // in m/s
    public final static float maxSpeed = (float) 19.44;

    // https://en.wikipedia.org/wiki/Rolling_resistance
    public final static float coeOfRollingResistance = (float) 0.0020;

    // https://en.wikipedia.org/wiki/Adhesion_railway
    public final static float coeOfFriction = (float) 0.35;

    public final static int maxPassenger = 222;
    /****** constants ******/

    private int UUID;
    private TrainController controller;
    private Block prevBlock = null;
    private Block currentBlock = null;
    private Boolean insideOneBlock = true;
    private Boolean stoppedAtStation = false;
    private float prevSpeed = 0;
    private float currentSpeed = 0;
    private float currentPower = 0;
    private float currentPosition = 0;
    private float prevAcceleration = 0;
    private float currentAcceleration = 0;
    private float currentGrade = 0;
    private float temperatureInside = 70;

    private float suggestedSpeed = 0;
    private float authority = 0;
    private float targetPower = 0;

    private int passengerCount = 0;
    private int crewCount = 2;
    private float currentWeight = (float) 40.9;

    private BooleanProperty leftDoorWorking = new SimpleBooleanProperty(true);
    private BooleanProperty rightDoorWorking = new SimpleBooleanProperty(true);
    private BooleanProperty lightWorking = new SimpleBooleanProperty(true);
    private BooleanProperty serviceBrakeWorking = new SimpleBooleanProperty(true);
    private BooleanProperty emergencyBrakeWorking = new SimpleBooleanProperty(true);
    private  BooleanProperty engineWorking = new SimpleBooleanProperty(true);

    // true means on
    private BooleanProperty leftDoorState = new SimpleBooleanProperty(false);
    private BooleanProperty rightDoorState = new SimpleBooleanProperty(false);
    private BooleanProperty lightState = new SimpleBooleanProperty(false);
    private BooleanProperty serviceBrakeState = new SimpleBooleanProperty(false);
    private BooleanProperty emergencyBrakeState = new SimpleBooleanProperty(false);
    
    private StringProperty beacon = new SimpleStringProperty("");

    private StringProperty suggestedSpeedString = new SimpleStringProperty("");
    private StringProperty currentSpeedString = new SimpleStringProperty("");
    private StringProperty authorityString = new SimpleStringProperty("");
    private StringProperty currentPowerString = new SimpleStringProperty("");
    private StringProperty passengerCountString = new SimpleStringProperty("");
    private StringProperty currentWeightString = new SimpleStringProperty("");
    private StringProperty currentAccelerationString = new SimpleStringProperty("");
    private StringProperty currentGradeString = new SimpleStringProperty("");
    private StringProperty temperatureInsideString = new SimpleStringProperty("");

    public Train(int id, TrainController trainController, Block currentBlock) {
        UUID = id;
        controller = trainController;
        this.currentBlock = currentBlock;
    }

    public void setBlock(Block block) {
        prevBlock = currentBlock;
        currentBlock = block;
        currentBlock.setTrain(this);
        insideOneBlock = true;
    }

    public void update() {
        if (currentBlock == null || removeFlag.getValue()) return;

        // spec: max speed 70 km/h = 19.44 m/s
        // service brake (2/3 load / 51.43 tons) 1.2 m/s^2, 61.7kN
        // emergency brake (2/3 load / 51.43 tons) 2.73 m/s^2, 140.4kN
        // max passenger 74 + 148 = 222
        // max power 120 * 4 = 480 kW
        // max emergency brake force 6 x 81kN = 486kN

        // TODO: beacon

        // pick up and drop passengers at station
        if (!stoppedAtStation && currentBlock instanceof Station && currentSpeed == 0) {
            stoppedAtStation = true;
            Random rand = new Random();
            int leavingPassengers = 0;
            int boardingPassengers = 0;
            if (passengerCount > 0) {
                leavingPassengers = rand.nextInt(passengerCount);
                passengerCount -= leavingPassengers;
            }
            int avaliableSpace = maxPassenger - passengerCount;
            if (avaliableSpace > 0) {
                boardingPassengers = rand.nextInt(avaliableSpace);
                passengerCount += boardingPassengers;
            }
            ((Station) currentBlock).addTicketsSold(boardingPassengers);
        }

        // update data
        currentWeight = emptyWeight + (passengerCount + crewCount) * passengerWeight;
        currentGrade = currentBlock.getGrade();

        // in radian
        // for seperating gravity direction
        float angle = (float)Math.atan(currentGrade / 100);
        float cosAngle = (float)Math.cos(angle);
        float sinAngle = (float)Math.sin(angle);
        // in kN
        float normalForce = currentWeight * gravity * cosAngle;
        float maxGripForce = normalForce * coeOfFriction;
        // in seconds
        float timeStep = ((float)Module.TIMESTEP) / 1000;

        // power (kW)
        currentPower = targetPower;
        if (!engineWorking.getValue()) currentPower = 0;
        // limit power
        if (currentPower > maxPower) {
            currentPower = maxPower;
        } else if (currentPower < 0) {
            currentPower = 0;
        }

        // force (kN)
        // P = Fv, F = P/v
        float force = 0;
        float brakingForce = 0;
        if (currentSpeed == 0) {
            if (currentPower > 0) force = maxForce;
        } else {
            force = currentPower / currentSpeed;
        }
        // limit force
        if (force > maxForce) {
            force = maxForce;
        }

        // braking
        if (emergencyBrakeState.getValue()) {
            force = 0;
            if (emergencyBrakeWorking.getValue()) {
                brakingForce = emergencyBrakeForce;
            }
        } else if (serviceBrakeState.getValue()) {
            force = 0;
            if (serviceBrakeWorking.getValue()) {
                brakingForce = serviceBrakeForce;
            }
        }

        // rolling resistance (kN)
        float rollingResistance = normalForce * coeOfRollingResistance;
        // sum forces (kN)
        // rollingResistance is always pointing backward because the train only goes forward
        float totalForce = force - brakingForce - rollingResistance;
        // limit to maximum gripping force
        if (totalForce > maxGripForce) {
            totalForce = maxGripForce;
        } else if (totalForce < -maxGripForce) {
            totalForce = -maxGripForce;
        }

        // acceleration (m / s^2)
        prevAcceleration = currentAcceleration;
        // F = ma, a = F/m
        currentAcceleration = totalForce / currentWeight - (gravity * sinAngle);

        // velocity (m / s)
        prevSpeed = currentSpeed;
        currentSpeed += (timeStep / 2) * (currentAcceleration + prevAcceleration);
        // limit speed
        if (currentSpeed > 19.44) {
            currentSpeed = maxSpeed;
            currentAcceleration = 0;
        } else if (currentSpeed < 0) {
            // only forward direction for this train model
            currentSpeed = 0;
            currentAcceleration = 0;
        }
        
        // position (m)
        currentPosition += (timeStep / 2) * (currentSpeed + prevSpeed);

        boolean debug = false;
        if (debug) {
            System.out.println("Train " + UUID + ": ");
            System.out.println("currentGrade " + currentGrade + " %");
            System.out.println("currentPower: " + currentPower + " kW");
            System.out.println("force: " + force + " kN");
            System.out.println("brakingForce: " + brakingForce + " kN");
            System.out.println("rollingResistance: " + rollingResistance + " kN");
            System.out.println("totalForce: " + totalForce + " kN");
            System.out.println("maxGripForce: " + maxGripForce + " kN");
            System.out.println("currentAcceleration: " + currentAcceleration + " m/s^2");
            System.out.println("acceleration due to engine: " + (force / currentWeight) + " m/s^2");
            System.out.println("acceleration due to brakes: " + (brakingForce / currentWeight) + " m/s^2");
            System.out.println("acceleration due to grade: -" + (gravity * sinAngle) + " m/s^2");
            System.out.println("acceleration due to resistance: -" + (rollingResistance / currentWeight) + " m/s^2");
            System.out.println("currentSpeed: " + currentSpeed + " m/s");
            System.out.println("currentPosition: " + currentPosition + " m");
            System.out.println("currentBlockLength: " + currentBlock.getLength() + " m");
        }

        updateBlock();
        updateString();
    }

    private void updateBlock() {
        // check still inside current block
        // there is only forward direction
        if (currentPosition > currentBlock.getLength()) {
            currentPosition -= currentBlock.getLength();
            nextBlock();
        }
        // check if the end of the train leaves previous block
        if (!insideOneBlock) {
            //if (currentPosition - length > 0) prevBlock.setTrain(null);
            prevBlock.setTrain(null);
            insideOneBlock = true;
        }
    }

    private void nextBlock() {
        insideOneBlock = false;
        stoppedAtStation = false;
        Block nextBlock = null;

        // if its a connected edge and it's not the previous block
        for (Edge edge: currentBlock.getEdges()){
            Block edgeBlock = edge.getBlock();
            if(edge.getConnected() && !edgeBlock.equals(prevBlock)){
                nextBlock = edgeBlock;
            }
        };
        if (nextBlock == null) {
            new CrashUI("Train crashed", getName() + " crashed into end of track");
            crashed = true;
            return;
        }

        assert nextBlock != null;
        System.out.println(getName() + " - Next Block: " + nextBlock.getBlockNumber());

        // check if we arrived in yard
        if (nextBlock instanceof Yard) {
            currentBlock.setTrain(null);
            destroyTrain();
            return;
        } else if (nextBlock instanceof Station) {
            beacon.setValue(((Station) currentBlock).getBeacon());
        } else {
            beacon.setValue("");
        }

        prevBlock = currentBlock;
        currentBlock = nextBlock;
        currentBlock.setTrain(this);
        controller.nextBlock();
        
    }


    // to be removed
    public void setSpeed(float speed){
        //currentSpeed = speed;
    }
    public void setAuthority(float authority){
        //this.authority = authority;
    }

    public int getUUID() {
        return UUID;
    }

    public void destroyTrain() {
        controller.destroy();
        removeFlag.setValue(true);
    }


    // called by track model only
    public void setTrain(float suggestedSpeed, float authority) {
        this.suggestedSpeed = suggestedSpeed;
        this.authority = authority;
        controller.setTrain(suggestedSpeed, authority);
    }


    /****** called by train controller and GUI ******/
    public void setPower(float power) {
        targetPower = power;
    }

    public void setTemperature(float temperature) {
        temperatureInside = temperature;
    }

    public void setLeftDoor(boolean state) {
        if (leftDoorWorking.getValue()) leftDoorState.setValue(state);
    }

    public void setRightDoor(boolean state) {
        if (rightDoorWorking.getValue()) rightDoorState.setValue(state);
    }

    public void setLight(boolean state) {
        if (lightWorking.getValue()) lightState.setValue(state);
    }

    public void setServiceBrake(boolean state) {
        if (serviceBrakeWorking.getValue()) serviceBrakeState.setValue(state);
    }

    public void setEmergencyBrake(boolean state) {
        if (emergencyBrakeWorking.getValue()) emergencyBrakeState.setValue(state);
    }

    public BooleanProperty getLeftDoorState() {
        return leftDoorState;
    }

    public BooleanProperty getRightDoorState() {
        return rightDoorState;
    }

    public BooleanProperty getLightState() {
        return lightState;
    }

    public BooleanProperty getServiceBrakeState() {
        return serviceBrakeState;
    }

    public BooleanProperty getEmergencyBrakeState() {
        return emergencyBrakeState;
    }

    public BooleanProperty getLeftDoorWorking() {
        return leftDoorWorking;
    }

    public BooleanProperty getRightDoorWorking() {
        return rightDoorWorking;
    }

    public BooleanProperty getLightWorking() {
        return lightWorking;
    }

    public BooleanProperty getServiceBrakeWorking() {
        return serviceBrakeWorking;
    }

    public BooleanProperty getEmergencyBrakeWorking() {
        return emergencyBrakeWorking;
    }
    
    public BooleanProperty getEngineWorking() {
        return engineWorking;
    }

    public StringProperty getBeacon() {
        return beacon;
    }
    /****** called by train controller and GUI ******/



    /****** for GUI ******/
    private void updateString() {
        suggestedSpeedString.setValue(UICommon.metersPerSecondToMilesPerHour(suggestedSpeed) + " mph");
        currentSpeedString.setValue(UICommon.metersPerSecondToMilesPerHour(currentSpeed) + " mph");
        authorityString.setValue(authority + " block");
        currentPowerString.setValue(UICommon.roundToOneDecimal(currentPower) + " kW");
        passengerCountString.setValue(passengerCount + "");
        currentWeightString.setValue(currentWeight + " tons");
        currentAccelerationString.setValue(UICommon.metersPerSecondSquaredToFeetPerSecondSquared(currentAcceleration) + " ft/s^2");
        currentGradeString.setValue(currentGrade + " %");
        temperatureInsideString.setValue(temperatureInside + " F");
    }

    public StringProperty getName() {
        return new SimpleStringProperty("Train " + UUID);
    }

    public StringProperty getSuggestedSpeed() {
        return suggestedSpeedString;
    }

    public StringProperty getCurrentSpeed() {
        return currentSpeedString;
    }

    public StringProperty getAuthority() {
        return authorityString;
    }

    public StringProperty getCurrentPower() {
        return currentPowerString;
    }

    public StringProperty getPassengetCount() {
        return passengerCountString;
    }

    public StringProperty getCurrentWeight() {
        return currentWeightString;
    }

    public StringProperty getCurrentAcceleration() {
        return currentAccelerationString;
    }

    public StringProperty getCurrentGrade() {
        return currentGradeString;
    }

    public StringProperty getTemperatureInside() {
        return temperatureInsideString;
    }
    /****** for GUI ******/

}