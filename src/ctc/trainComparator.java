package src.ctc;
import java.util.Comparator;


public class trainComparator implements Comparator<CTCTrain> {
    public trainComparator(){

    }

    @Override
    public int compare(CTCTrain train1, CTCTrain train2){
        if (train1.getTrainID() > train2.getTrainID()){
            return 1;
        }
        else if (train1.getTrainID() < train2.getTrainID()){
            return -1;
        }
        else {return 0;}
    }
}