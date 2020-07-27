package application.Game.Node;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

import static application.Config.ColorType;
import static application.Config.UNIT_SIZE;

public class Block extends Object {
    protected Image image;

    // 绘图旋转角度,默认为0度
    protected double direction = 0;

    // 块类的基本构造函数
    // color不只是显色用,在判断时也需要,因此一定要加
    public Block(GraphicsContext gc, ColorType color, double x, double y) {
        super(gc, color, x, y, UNIT_SIZE, UNIT_SIZE);
    }

    public Block(GraphicsContext gc, ColorType color, Image image, double x, double y) {
        super(gc, color, x, y);
        setImage(image);
    }

    public Block(GraphicsContext gc, ColorType color, double x, double y, double width, double height) {
        super(gc, color, x, y, width, height);
    }


    @Override
    public void draw() {
        if (isVisible) {
            gc.save();
            // 旋转控制
            Rotate r = new Rotate(direction / Math.PI * 180 + 90, getX(false), getY(false));
            gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());

            // 如果是图片块
            if (image != null) {
                gc.drawImage(image, 0, 0, image.getWidth(), image.getHeight(),
                    getX(true), getY(true), getWidth(), getHeight());
            } else {
                gc.setFill(color.get());
                gc.fillRect(getX(true), getY(true), getWidth(), getHeight());
            }
            gc.restore();
        }
    }

    // 图片部分
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
        setWidth(image.getWidth() / 2);
        setHeight(image.getHeight() / 2);
    }

    // 原型,内部使用
    public void move(double x, double y) {
        setY(getY() + y / UNIT_SIZE);
        setX(getX() + x / UNIT_SIZE);
    }

    /**
     * 位置部分
     *
     * @param type 如果为false得到中点坐标,不然就拿到绘图坐标.
     */
    public double getX(boolean type) {
        if (type)
            return getX() * UNIT_SIZE;
        else
            return getX() * UNIT_SIZE + getWidth() / 2;
    }

    public double getY(boolean type) {
        if (type)
            return getY() * UNIT_SIZE;
        else
            return getY() * UNIT_SIZE + getHeight() / 2;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }
}