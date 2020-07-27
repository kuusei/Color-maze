package application.Game;

import application.Game.Map.Canvas;
import application.Game.Node.Block;
import javafx.application.Platform;

import java.util.Date;

import static application.Config.*;

public class MiniController extends Controller {

    public MiniController(Canvas canvas) {
        super();
        this.canvas = canvas;
        player = canvas.getMap().getPlayers().get(0);
        time = new Date().getTime();
        thread.start();
        System.out.println("canvas = " + canvas);
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    Thread thread = new Thread(() -> {
        while (isRunning) {
            if (event != null) {
                Platform.runLater(() -> {
                    handle(event);
                    System.out.println("player = " + player);
                    score.setText(String.valueOf((new Date().getTime() - time)* 10));
                    if ((new Date().getTime() - time)* 10 > 100000) {
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
                        player.move(x, y);
                        Block block = isCollision(canvas.getMap().getBlocks());
                        if (block != null) {
                            System.out.println("gameover");
                            isRunning = false;
                            new application.Home.Main().start(mainStage);
                        }
                    }

                    for (Block block : canvas.getMap().getBlocks()) {
                        DirectionX = player.getX(false) - block.getX(false);
                        DirectionY = player.getY(false) - block.getY(false);
                        QuadraticSum = Math.pow(DirectionX, 2) + Math.pow(DirectionY, 2);
                        x = DirectionX * Math.abs(DirectionX) / QuadraticSum;
                        y = DirectionY * Math.abs(DirectionY) / QuadraticSum;
                        if (true) {
                            if (!(y > 0.99 || x > 0.99)) {
                                double acos = Math.acos(x / Math.sqrt(x * x + y * y));
                                block.setDirection(y > 0 ? acos : 2 * Math.PI - acos);
                            }
                            block.move(x * 2, y * 2);
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
        canvas = null;
    });
}