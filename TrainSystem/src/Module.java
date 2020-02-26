package trainGame;
import java.time.LocalDateTime;


public abstract class Module 
{ 
    final int YEAR= 2020;
    final int MONTH= 1;
    final int DAY= 1;
    final int HOUR= 9;
    final int MINUTE= 30;
    final int SECOND= 0;
    final int PERIOD= 1;
    
    LocalDateTime date;

    public Module() {
        this.date= LocalDateTime.of(YEAR, MONTH, DAY, HOUR, MINUTE, SECOND);
    }

    public void tickTock() {
        this.date.plusSeconds( PERIOD);
    }
}