package src.ctc;
import java.util.Comparator;

import src.track_module.Block;

public class blockNumberComparator implements Comparator<Block> {
    public blockNumberComparator(){

    }

    @Override
    public int compare(Block block1, Block block2){
        if (block1.getBlockNumber() > block2.getBlockNumber()){
            return 1;
        }
        else if (block1.getBlockNumber() < block2.getBlockNumber()){
            return -1;
        }
        else {return 0;}
    }
}