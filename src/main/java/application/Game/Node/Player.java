package application.Game.Node;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static application.Config.*;
import static application.Config.getPlane;

public class Player extends Block {
    // 传送门延时器
    private int isPortal = 10;

    // 玩家类的基本构造函数
    public Player(GraphicsContext gc, ColorType color, double x, double y) {
        super(gc, color, x, y, UNIT_SIZE, UNIT_SIZE);
        setImage(getPlane(color));
    }

    public int getIsPortal() {
        return isPortal;
    }

    public void setIsPortal(int isPortal) {
        this.isPortal = isPortal;
    }

    @Override
    public void setImage(Image image) {
        this.image = image;
        setWidth(image.getWidth() / 2);
        setHeight(image.getHeight() / 2);
    }
}