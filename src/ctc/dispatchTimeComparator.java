package src.ctc;
import java.util.Comparator;

public class dispatchTimeComparator implements Comparator<CTCTrain> {
    public dispatchTimeComparator(){

    }

    @Override
    public int compare(CTCTrain train1, CTCTrain train2){
      return train1.getDispatchTime().compareTo(train2.getDispatchTime());
    }
}