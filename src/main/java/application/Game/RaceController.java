package application.Game;

import application.Game.Map.Canvas;
import application.Game.Node.Player;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Date;

import static application.Config.*;
import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public class RaceController extends Controller {
    protected Player p1;
    protected Player p2;
    protected KeyEvent eventKey;
    protected double p1Speed = 0;
    protected double p2Speed = 0;
    protected int p1State[] = new int[2];
    protected int p2State[] = new int[2];


    public RaceController(Canvas canvas) {
        super();
        this.canvas = canvas;
        p1 = canvas.getMap().getPlayers().get(0);
        p2 = canvas.getMap().getPlayers().get(1);
        time = new Date().getTime();

        thread.start();
        System.out.println("canvas = " + canvas);
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void handle(Event event) {
        try {
            KeyCode code = ((KeyEvent) event).getCode();
            if (event.getEventType() == KEY_PRESSED) {
                switch (code) {
                    case UP:
                        p1State[0] = 1;
                        break;
                    case DOWN:
                        p1State[0] = -1;
                        break;
                    case RIGHT:
                        p1State[1] = -1;
                        break;
                    case LEFT:
                        p1State[1] = 1;
                        break;
                    case W:
                        p2State[0] = 1;
                        break;
                    case S:
                        p2State[0] = -1;
                        break;
                    case D:
                        p2State[1] = -1;
                        break;
                    case A:
                        p2State[1] = 1;
                    case Q:
                        crack = 100;
                }
            } else {
                switch (code) {
                    case UP:
                    case DOWN:
                        p1State[0] = 0;
                        break;
                    case LEFT:
                    case RIGHT:
                        p1State[1] = 0;
                        break;
                    case W:
                    case S:
                        p2State[0] = 0;
                        break;
                    case A:
                    case D:
                        p2State[1] = 0;
                }
            }
            if (code == KeyCode.R) {
                player = null;
                nowLevel--;
                isRunning = false;
                new application.Game.Main().start(mainStage);
            } else {
            }
        } catch (Exception ex) {
            ex = null;
            try {
                this.event = (MouseEvent) event;
            } catch (Exception exx) {
                ex.printStackTrace();
            }
        }
    }

    Thread thread = new Thread(() -> {
        while (isRunning) {
            Platform.runLater(() -> {
                if (p1State[0] > 0 && p1Speed <= MOVE_SPEED / 3.) {
                    p1Speed += 0.05;
                } else if (p1State[0] < 0) {
                    p1Speed -= 0.3;
                }

                if (p2State[0] > 0 && p2Speed <= MOVE_SPEED / 3.) {
                    p2Speed += 0.05;
                } else if (p2State[0] < 0) {
                    p2Speed -= 0.3;
                }

                p1.setDirection(p1.getDirection() - (p1State[1] * 0.02));
                p2.setDirection(p2.getDirection() - (p2State[1] * 0.02));

                if (p1Speed > 0) {
                    for (int i = 0; i < (int) (p1Speed); i++) {
                        p1Speed -= 0.005;
                        if (isCollision(Math.cos(p1.getDirection()), Math.sin(p1.getDirection()),p1)) {
                            p1.move(Math.cos(p1.getDirection()), Math.sin(p1.getDirection()));
                        }
                    }
                }

                if (p2Speed > 0) {
                    for (int i = 0; i < (int) (p2Speed); i++) {
                        p2Speed -= 0.005;
                        if (isCollision(Math.cos(p1.getDirection()), Math.sin(p1.getDirection()),p2)) {
                            p2.move(Math.cos(p2.getDirection()), Math.sin(p2.getDirection()));
                        }
                    }
                }

                score.setText(String.valueOf((new Date().getTime() - time) * 10));

                // 终点判断
                if (isCollision(canvas.getMap().getEnds(), p1) != null) {
                    this.setRunning(false);
                    new application.Home.Main().start(mainStage);
                }
            });
            try {
                Thread.sleep(SLEEP);
            } catch (InterruptedException ignored) {
            }
        }
    });
}