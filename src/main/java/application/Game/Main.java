package application.Game;

import application.Game.Map.Canvas;
import application.Game.Map.Data;
import application.Home.Util.MyButton;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static application.Config.*;

public class Main extends Application {
    private Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        MyButton back = new MyButton("", 10, 10, 50, 40, "re-button");
        //返回开始游戏界面
        back.setOnAction(e -> {
            controller.setRunning(false);
            controller = null;
            new application.Home.Main().start(mainStage);
        });

        CANVAS_WEIGHT = (int) (Data.LevelMap[2][0] * UNIT_SIZE);
        CANVAS_HEIGHT = (int) (Data.LevelMap[2][1] * UNIT_SIZE);
        Canvas canvas = new Canvas(CANVAS_WEIGHT, CANVAS_HEIGHT, nowLevel);
        switch (gameMode){
            case main:
                controller = new Controller(canvas);
                break;
            case mini:
                controller = new MiniController(canvas);
                break;
            case race:
                controller = new RaceController(canvas);
        }
        root.getChildren().addAll(canvas, back, score, myMusic);

        Scene scene = new Scene(root, MAP_WEIGHT, MAP_HEIGHT);
        canvas.setVisible(true);
        scene.getStylesheets().add("start.css");
        scene.getRoot().setStyle("-fx-background-color: #000");
        scene.setOnKeyPressed(controller);
        scene.setOnKeyReleased(controller);
        scene.setOnMouseMoved(controller);
        scene.setOnMouseEntered(controller);

        mainStage.setScene(scene);
    }
}