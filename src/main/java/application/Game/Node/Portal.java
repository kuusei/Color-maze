package application.Game.Node;

import static application.Config.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;


public class Portal extends Block {
    private Portal other;

    public Portal(GraphicsContext gc, ColorType color, double x, double y) {
        super(gc, color, x, y, UNIT_SIZE, UNIT_SIZE);
    }

    public Portal(GraphicsContext gc, ColorType color, Image image, double x, double y) {
        super(gc, color, x, y);
        setImage(image);
    }

    @Override
    public void draw() {
        if (isVisible){
            gc.save();
            // 旋转控制
            Rotate r = new Rotate(direction / Math.PI * 180 + 90, getX(false), getY(false));
            gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());

            // 如果是图片块
            if (image != null)
                gc.drawImage(image, 0, 0, image.getWidth(), image.getHeight(),
                    getX(true), getY(true), getWidth(), getHeight());
            else {
                System.out.println("test");
                gc.setStroke(color.get());
                gc.strokeOval(getX(true), getY(true), getWidth(), getHeight());
            }
            gc.restore();
        }
    }

    public Portal getOther() {
        return other;
    }

    public void setOther(Portal other) {
        this.other = other;
    }
}
