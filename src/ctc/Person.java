<<<<<<< HEAD:src/ctc/Person.java
package src.ctc; 

=======
package CTC;
>>>>>>> jason:TrainSystem/src/CTC/Person.java
public class Person {

    private String firstName = null;
    private String currPos = null;
    private String destination = null;
    private String suggestedSpeed = null;

    public Person() {
    }

    public Person(String firstName, String currPos) {
        this.firstName = firstName;
        this.currPos = currPos;
    }

    public Person(String firstName) {
        this.firstName = firstName;
    }

    public Person(String firstName, String currPos, String destination, String suggestedSpeed){
        this.firstName = firstName;
        this.currPos = currPos;
        this.destination = destination;
        this.suggestedSpeed = suggestedSpeed;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCurrPos() {
        return currPos;
    }

    public void setCurrPos(String currPos) {
        this.currPos = currPos;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSuggestedSpeed() {
        return suggestedSpeed;
    }

    public void setSuggestedSpeed(String suggestedSpeed) {
        this.suggestedSpeed = suggestedSpeed;
    }

}