
package src.ctc;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import src.track_module.Block;




public class CTCBlock extends Block{

    private BooleanProperty closed = new SimpleBooleanProperty(false);
    public BooleanProperty closedProperty() {return closed;};

    public CTCBlock(String line, char section, int blockNumber, int length, float speedLimit, float grade, 
            float elevation, float cummElevation, boolean underground, int xCorrdinate, int yCorrdinate){
    super(line, section, blockNumber, length, speedLimit, grade, elevation, cummElevation, underground, xCorrdinate, yCorrdinate);
    }

    public boolean getClosed() { return closedProperty().get();}
    public void setClosed(boolean closed){closedProperty().set(closed);};

}