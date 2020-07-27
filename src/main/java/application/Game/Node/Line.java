package application.Game.Node;

import static application.Config.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public class Line extends Object {
    protected double direction = 0;
    protected Image image;

    public Line(GraphicsContext gc, ColorType color,Image image, double x, double y, double width, double height) {
        super(gc, color, x, y, width, height);
        this.image = image;
    }

    @Override
    public void draw() {
        gc.save();
        if (getWidth() - getX() == 0.0){
            direction = 0;
        } else if(getHeight()-getY() == 0.0){
            direction = -90;
        } else {
            direction = -45;
        }
        Rotate r = new Rotate(direction, getX(), getY());
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());

        gc.drawImage(image, 0, 0, image.getWidth(), image.getHeight(),
            getX(), getY(), UNIT_SIZE2, Math.sqrt(Math.pow(getHeight()-getY(), 2) + Math.pow(getWidth()-getX(), 2)));
        gc.restore();
    }

    @Override
    public double getX() {
        return super.getX() * UNIT_SIZE + UNIT_SIZE2 - UNIT_SIZE8;
    }

    @Override
    public double getY() {
        return super.getY() * UNIT_SIZE + UNIT_SIZE2;
    }

    @Override
    public double getWidth() {
        return super.getWidth() * UNIT_SIZE + UNIT_SIZE2 - UNIT_SIZE8;
    }

    @Override
    public double getHeight() {
        return super.getHeight() * UNIT_SIZE + UNIT_SIZE2;
    }
}
