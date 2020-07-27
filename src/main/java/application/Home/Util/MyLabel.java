package application.Home.Util;

import javafx.scene.control.Label;

public class MyLabel extends Label {
    public MyLabel(String text, double x, double y, int Width, int Height, String css) {
        super(text);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setPrefSize(Width, Height);
        this.getStyleClass().addAll(css);
    }
}
