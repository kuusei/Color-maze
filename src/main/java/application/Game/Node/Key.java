package application.Game.Node;

import application.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Key extends Block {
    private Line line;

    public Key(GraphicsContext gc, Config.ColorType color, Image image, double x, double y,Line line) {
        super(gc, color, image, x, y);
        this.line = line;
    }

    public Key(GraphicsContext gc, Config.ColorType color, double x, double y, double width, double height ,Line line) {
        super(gc, color, x, y, width, height);
        this.line = line;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }
}
