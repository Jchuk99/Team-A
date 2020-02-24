public class Person {

    private String firstName = null;
    private String lastName = null;
    private String blockOpenClose = null;
    private int suggestedSpeed = 0;
    private int authority = 0;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String firstName) {
        this.firstName = firstName;
    }

    public Person(String firstName, String lastName, String blockOpenClose, int suggestedSpeed, int authority){
        this.firstName = firstName;
        this.lastName = lastName;
        this.blockOpenClose = blockOpenClose;
        this.suggestedSpeed = suggestedSpeed;
        this.authority = authority;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBlockOpenClose() {
        return blockOpenClose;
    }

    public void setBlockOpenClose(String blockOpenClose) {
        this.blockOpenClose = blockOpenClose;
    }

    public int getSuggestedSpeed() {
        return suggestedSpeed;
    }

    public void setSuggestedSpeed(int suggestedSpeed) {
        this.suggestedSpeed = suggestedSpeed;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

}