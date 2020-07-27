package application;

import application.Home.Util.MyButton;
import application.Home.Util.MyMusic;
import application.Home.Util.MyView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Config {
    public enum ColorType {
        red(255, 0, 0), blue(0, 0, 255), green(0, 255, 0),
        yellow(255, 255, 0), purple(255, 0, 255), cyan(0, 255, 255),
        black(0, 0, 0), white(255, 255, 255);

        double r;
        double g;
        double b;

        ColorType(double r, double g, double b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public static ColorType get(double r, double g, double b) {
            for (ColorType color : ColorType.values()) {
                if (color.r == r && color.g == g && color.b == b) {
                    return color;
                }
            }
            return null;
        }

        public Color get() {
            return Color.color(this.r / 255., this.g / 255., this.b / 255.);
        }
    }

    // 线程相关
    public static final int SLEEP = 1000 / 180;

    // 资源文件
    public static final String src = "src/main/resources/";
    public static final String[] SONGS = {src + "background1.mp3"};
    public static final Image MAP = new Image("backgronud2.png");

    // 最小值和最大值
    public static final double MIN = 65564;

    // 地图大小
    public static final int MAP_SIZE = 256;
    public static final double MAP_WEIGHT = 1920 / 1.2;
    public static final double MAP_HEIGHT = 1012 / 1.2;
    public static int CANVAS_WEIGHT = 1000;
    public static int CANVAS_HEIGHT = 1000;
    // 单元格大小
    public static final double UNIT_SIZE = 32;
    public static final double UNIT_SIZE2 = UNIT_SIZE / 2;
    public static final double UNIT_SIZE4 = UNIT_SIZE2 / 2;
    public static final double UNIT_SIZE8 = UNIT_SIZE4 / 2;
    public static final double UNIT_SIZE16 = UNIT_SIZE8 / 2;

    public static final double Collision = UNIT_SIZE8;

    public static final int MOVE_SPEED = (int) (UNIT_SIZE8);


    // 主场景
    public static Stage mainStage = null;
    public static boolean r = false;
    public static int nowLevel = 1;

    // 登陆页面
    public static MyView mediaView = null;
    public static MyMusic myMusic = null;
    public static String loginVideo = "test.mp4";
    public static String coverVideo = "test.mp4";

    public static String plane = "2";
    public static MyButton score = new MyButton("999999", MAP_WEIGHT * 0.86, MAP_HEIGHT * 0.01 - 5, 240, 45);

    public static Image redPlane = new Image("plane/red" + plane + ".png");
    public static Image cyanPlane = new Image("plane/cyan" + plane + ".png");
    public static Image greenPlane = new Image("plane/green" + plane + ".png");
    public static Image purplePlane = new Image("plane/purple" + plane + ".png");
    public static Image bluePlane = new Image("plane/blue" + plane + ".png");
    public static Image yellowPlane = new Image("plane/yellow" + plane + ".png");

    public static Image getPlane(ColorType colorType) {

        switch (colorType) {
            case red:
                return redPlane;
            case blue:
                return bluePlane;
            case cyan:
                return cyanPlane;
            case green:
                return greenPlane;
            case purple:
                return purplePlane;
            case yellow:
                return yellowPlane;
        }
        return redPlane;
    }

    public static Image redLine = new Image("line/red" + ".png");
    public static Image cyanLine = new Image("line/cyan" + ".png");
    public static Image greenLine = new Image("line/green" + ".png");
    public static Image purpleLine = new Image("line/purple" + ".png");
    public static Image blueLine = new Image("line/blue" + ".png");
    public static Image yellowLine = new Image("line/yellow" + ".png");
    public static Image whiteLine = new Image("line/white" + ".png");


    public static Image getLine(ColorType colorType) {
        switch (colorType) {
            case red:
                return redLine;
            case blue:
                return blueLine;
            case cyan:
                return cyanLine;
            case green:
                return greenLine;
            case purple:
                return purpleLine;
            case yellow:
                return yellowLine;
            case white:
                return whiteLine;
        }
        return whiteLine;
    }

    public enum GameMode{
        main,mini,race
    }
    public static GameMode gameMode = GameMode.race;

    public static int crack = 0;
}