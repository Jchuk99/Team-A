package src;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CrashUI extends Stage {

    final int width = 300;
    final int height = 200;

    public CrashUI(String title, String text) {
        setTitle(title);
        final VBox fullScreen = new VBox(10, UICommon.createText(text));

        fullScreen.setPadding(new Insets(10));
        
        setScene(new Scene(fullScreen, width, height));
        show();
    }

}