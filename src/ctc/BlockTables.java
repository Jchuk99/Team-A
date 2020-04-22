package src.ctc;

import javafx.scene.control.TableView;
import src.ctc.CTCBlockConstructor.CTCStation;
import src.track_module.Block;

public class BlockTables {

    public TableView<CTCStation> stationTable;
    public TableView<Block> greenBlockTable;
    public TableView<Block> redBlockTable;

    public BlockTables(TableView<CTCStation> st, TableView<Block> green, TableView<Block> red){
        stationTable = st;
        greenBlockTable = green;
        redBlockTable = red;
    }

}