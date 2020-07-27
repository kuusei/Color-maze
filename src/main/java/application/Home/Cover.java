package application.Home;

import application.Home.Util.MyButton;
import application.Home.Util.MyMusic;
import application.Home.Util.MyView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import static application.Config.*;

public class Cover extends BorderPane {
    public Cover() {
        Group group = new Group();
        gameMode = GameMode.main;
        nowLevel = 1;
        System.out.println("myMusic = " + myMusic);
        // 视频部分
        if (mediaView == null) {
            System.out.println("mediaView = " + mediaView);
            mediaView = new MyView(coverVideo, this);
        }
        if (myMusic == null) {
            myMusic = new MyMusic(SONGS);
        }

        // 开始游戏按钮
        Button title = new MyButton("Color maze", (MAP_WEIGHT) * 8 / 10 - 150, (MAP_HEIGHT) * 2 / 8, 300, 90);
        Button play = new MyButton("play", (MAP_WEIGHT) * 8 / 10 - 65, (MAP_HEIGHT - 130) * 4 / 8, 130, 60, "game-button-xs");
        Button choice = new MyButton("level", (MAP_WEIGHT) * 8 / 10 - 75, (MAP_HEIGHT) * 4 / 8, 150, 60, "game-button-xs");
        Button setting = new MyButton("⚙", MAP_WEIGHT * 0.01, MAP_HEIGHT * 0.01, 45, 45);
        Button miniGame = new MyButton("⏰", MAP_WEIGHT * 0.01, MAP_HEIGHT * 0.92, 45, 45, "game-button-sm");
        miniGame.setStyle("-fx-font-family: 'Segoe UI Black'");
        Button raceGame = new MyButton("✈", MAP_WEIGHT * 0.04, MAP_HEIGHT * 0.92, 45, 45, "game-button-sm");
        raceGame.setStyle("-fx-font-family: 'Segoe UI Black'");

        Button exit = new MyButton("x", MAP_WEIGHT * 0.96, MAP_HEIGHT * 0.01 - 5, 45, 45);

        group.getChildren().addAll(mediaView, title, play, choice, setting, exit, miniGame, raceGame, myMusic);
        mainStage.setScene(new Scene(group, MAP_WEIGHT, MAP_HEIGHT));

        this.getChildren().addAll(mediaView, title, play, choice, setting, exit, miniGame, raceGame, myMusic);
        // 场景切换函数
        Main.switchScene(this);

        //开始游戏
        play.setOnAction(e -> {
            new Story();
        });

        //关卡选择
        choice.setOnAction(e -> {
            new Level();
        });

        //设置
        setting.setOnAction(e -> {
            mediaView.stop();
            mediaView = null;
            new Setting();
        });

        miniGame.setOnAction(e -> {
            mediaView.stop();
            mediaView = null;
            mainStage.setScene(null);
            gameMode = GameMode.mini;
            new application.Game.Main().start(mainStage);
        });

        raceGame.setOnAction(e -> {
            mediaView.stop();
            mediaView = null;
            mainStage.setScene(null);
            gameMode = GameMode.race;
            new application.Game.Main().start(mainStage);
        });

        exit.setOnAction(e -> {
            System.exit(0);
        });
    }
}
