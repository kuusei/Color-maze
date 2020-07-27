package application.Game.Node;

// 引入静态变量

import static application.Config.*;

import javafx.scene.canvas.GraphicsContext;

public abstract class Object {
    protected GraphicsContext gc;
    // 是否可见
    protected boolean isVisible = true;
    protected ColorType color;

    private double x;
    private double y;
    // 定义默认长宽
    private double width = UNIT_SIZE;
    private double height = UNIT_SIZE;

    public Object(GraphicsContext gc, ColorType color, double x, double y) {
        this.gc = gc;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public Object(GraphicsContext gc, ColorType color, double x, double y, double width, double height) {
        this(gc, color, x, y);
        this.width = width;
        this.height = height;
    }

    public abstract void draw();

    public void setLocation(double x, double y) {
        setX(x);
        setY(y);
    }

    public ColorType getColor() {
        return color;
    }

    public void setColor(ColorType color) {
        this.color = color;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}
