package application.Home.Util;

import javafx.scene.control.Button;

public class MyButton extends Button {
    public MyButton(String text, double x, double y) {

        super(text);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setPrefSize(130, 60);
        this.getStyleClass().addAll("game-button");
    }

    public MyButton(String text, double x, double y, int Width, int Height) {
        super(text);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setPrefSize(Width, Height);
        this.getStyleClass().addAll("game-button");
    }

    public MyButton(String text, double x, double y, int Width, int Height, String css) {
        super(text);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setPrefSize(Width, Height);
        this.getStyleClass().addAll(css);
    }
}
