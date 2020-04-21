package src.ctc;
import java.util.Comparator;

public class dispatchTimeComparator implements Comparator<CTCTrain> {
    public dispatchTimeComparator(){

    }

    @Override
    public int compare(CTCTrain train1, CTCTrain train2){
        //TODO: check if works, -1 for reverse ordering.
      return train1.getDispatchTime().compareTo(train2.getDispatchTime());
    }
}