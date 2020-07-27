package application.Game;

import application.Game.Map.Canvas;
import application.Game.Node.Object;
import application.Game.Node.*;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Date;
import java.util.List;

import static application.Config.*;
import static application.Game.Util.Collision.IsCollision;
import static application.Game.Util.Distance.point2Line;

/**
 * 游戏控制器类
 *
 * @version 1.3
 */
public class Controller implements EventHandler<Event> {
    protected Player player;
    protected Canvas canvas;
    protected MouseEvent event;
    protected long time = 0;
    protected boolean isRunning = true;

    public Controller(Canvas canvas) {
        this.canvas = canvas;
        player = canvas.getMap().getPlayers().get(0);
        if (player.getY(false) - MAP_HEIGHT < 0) {
            canvas.setLayoutY(0);
        } else if (player.getY(false) + MAP_HEIGHT > CANVAS_HEIGHT) {
            canvas.setLayoutY(-CANVAS_HEIGHT + (MAP_HEIGHT));
        } else {
            canvas.setLayoutY(-player.getY(false) + MAP_HEIGHT / 2.);
        }

        if (player.getX(false) + MAP_WEIGHT > CANVAS_WEIGHT) {
            canvas.setLayoutX(-CANVAS_WEIGHT + (MAP_WEIGHT));
        } else if (player.getX(false) - MAP_WEIGHT < 0) {
            canvas.setLayoutX(0);
        } else {
            canvas.setLayoutX(-player.getX(false) + MAP_WEIGHT / 2.);
        }

        time = 99999 + new Date().getTime();

        thread.start();
    }

    public Controller() {

    }

    @Override
    public void handle(Event event) {
        try {
            KeyCode code = ((KeyEvent) event).getCode();
            if (code == KeyCode.R) {
                player = null;
                nowLevel--;
                isRunning = false;
                new application.Game.Main().start(mainStage);
            } else if (code == KeyCode.Q) {
                    crack = 100;
            } else {
                System.out.println(code);
            }
        } catch (Exception e) {
            e = null;
            try {
                this.event = (MouseEvent) event;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    /**
     * 碰撞判断函数(点和线之间判断,位置预测用)
     * 这个方法的主要目的是计算下一步位置的碰撞情况
     * 在本程序中,只有与线的碰撞判断需要计算下一步,主要是为了防止卡线
     * 程序内使用了一个crack全局变量,其目的是为了能够启动作弊模式
     *
     * @param x      是下一步移动x轴大小的值.
     * @param y      同上
     * @param player 为碰撞的主体
     * @return boolean 即碰撞与否
     * @throws NullPointerException 当player类没初始化的情况下，调用这个函数就有可能会导致此错误，
     *                              在基于Controller类子如果忽略这种情况,就会使player未初始化情况出现
     */
    boolean isCollision(double x, double y, Player player) {
        Line temp = null;
        double min = MIN;

        for (Object node : canvas.getMap().getLines()) {
            Line line = (Line) node;
            if (point2Line(player, line) < min) {
                min = point2Line(player, line);
                temp = line;
            }
        }
        if (temp != null) {
            if (!IsCollision(player, temp, x, y) || crack>0) {
                crack--;
                return true;
            } else {
                for (Key key : canvas.getMap().getKeys()) {
                    if (key.getLine() == temp && temp.isVisible()){
                        return false;
                    }
                }
                return temp.getColor().equals(player.getColor()) || !temp.isVisible();
            }
        } else {
            return true;
        }
    }

    boolean isCollision(double x, double y) {
        return isCollision(x, y, player);
    }

    // 块碰撞判断(原型,内部使用)
    <T> T isCollision(List<T> lists) {
        return isCollision(lists, player);
    }

    <T> T isCollision(List<T> lists, Player player) {
        for (T element : lists) {
            if (IsCollision(player, (Block) element)) {
                return element;
            }
        }
        return null;
    }

    // 主线程
    private Thread thread = new Thread(() -> {
        while (isRunning) {
            if (event != null) {
                Platform.runLater(() -> {
                    handle(event);

                    score.setText(String.valueOf((time - new Date().getTime()) * 10));
                    if (time - new Date().getTime() < 0) {
                        this.setRunning(false);
                        new application.Home.Main().start(mainStage);
                    }

                    // 移动
                    double DirectionX = event.getX() - canvas.getLayoutX() - player.getX(false);
                    double DirectionY = event.getY() - canvas.getLayoutY() - player.getY(false);
                    double QuadraticSum = Math.pow(DirectionX, 2) + Math.pow(DirectionY, 2);
                    double x = DirectionX * Math.abs(DirectionX) / QuadraticSum;
                    double y = DirectionY * Math.abs(DirectionY) / QuadraticSum;
                    if (!(y > 0.99 || x > 0.99)) {
                        double acos = Math.acos(x / Math.sqrt(x * x + y * y));
                        player.setDirection(y > 0 ? acos : 2 * Math.PI - acos);
                    }

                    for (int i = 0; i < MOVE_SPEED; i++) {
                        // 激光判断
                        if (isCollision(x, y)) {
                            player.move(x, y);
                            if (canvas.getLayoutX() - x > -CANVAS_WEIGHT + (MAP_WEIGHT) & canvas.getLayoutX() - x < 0 &
                                Math.abs(-canvas.getLayoutX() - player.getX(false)) < MAP_WEIGHT - 50 &
                                player.getX(false) > 350 & player.getX(false) < CANVAS_WEIGHT - 350
                            ) {
                                canvas.setLayoutX(canvas.getLayoutX() - x);
                            }
                            if (canvas.getLayoutY() - y > -CANVAS_HEIGHT + (MAP_HEIGHT) & canvas.getLayoutY() - y < 0 &
                                Math.abs(-canvas.getLayoutY() - player.getY(false)) < MAP_HEIGHT - 50 &
                                player.getY(false) > 350 & player.getY(false) < CANVAS_HEIGHT - 350
                            ) {
                                canvas.setLayoutY(canvas.getLayoutY() - y);
                            }
                        }
                        // 颜色块判断
                        Block block = isCollision(canvas.getMap().getBlocks());
                        if (block != null) {
                            player.setColor(block.getColor());
                            player.setImage(getPlane(player.getColor()));
                        }

                        // 传送门判断
                        if (player.getIsPortal() < 500) {
                            player.setIsPortal(player.getIsPortal() + 1);
                        }
                        Portal portal = isCollision(canvas.getMap().getPortals());
                        if (portal != null && player.getIsPortal() >= 500 && portal.getColor() == player.getColor()) {
                            player.setIsPortal(0);
                            // !!!!这里要拿原始坐标，draw会自动解析原始坐标的
                            player.setLocation(portal.getOther().getX(), portal.getOther().getY());
                            if (portal.getOther().getY(false) + MAP_HEIGHT > CANVAS_HEIGHT) {
                                canvas.setLayoutY(-CANVAS_HEIGHT + (MAP_HEIGHT));
                            } else if (portal.getOther().getY(false) - MAP_HEIGHT < 0) {
                                canvas.setLayoutY(0);
                            } else {
                                canvas.setLayoutY(-portal.getOther().getY(false) + MAP_HEIGHT / 2.);
                            }

                            if (portal.getOther().getX(false) + MAP_WEIGHT > CANVAS_WEIGHT) {
                                canvas.setLayoutX(-CANVAS_WEIGHT + (MAP_WEIGHT));
                            } else if (portal.getOther().getX(false) - MAP_WEIGHT < 0) {
                                canvas.setLayoutX(0);
                            } else {
                                canvas.setLayoutX(-portal.getOther().getX(false) + MAP_WEIGHT / 2.);
                            }
                        }

                        // 钥匙判断
                        Key key = isCollision(canvas.getMap().getKeys());
                        if (key != null) {
                            key.setVisible(false);
                            key.getLine().setVisible(false);
                        }
                        // 终点判断
                        if (isCollision(canvas.getMap().getEnds()) != null) {
                            this.setRunning(false);
                            new application.Home.Story();
                        }
                    }
                });
            }
            try {
                Thread.sleep(SLEEP);
            } catch (InterruptedException ignored) {
            }
        }
        canvas.setRunning(false);
    });

    public void setRunning(boolean running) {
        isRunning = running;
    }
}