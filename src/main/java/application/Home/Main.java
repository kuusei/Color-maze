package application.Home;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static application.Config.*;
import static javafx.stage.StageStyle.TRANSPARENT;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        System.out.println("primaryStage = " + primaryStage);
        mainStage = primaryStage;

        // 标题
        primaryStage.setTitle("色彩迷宫");
        // 可重置大小
        primaryStage.setResizable(false);

        // 调整大小
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.sizeToScene();
        primaryStage.setX((screenBounds.getWidth() - MAP_WEIGHT) / 2);
        primaryStage.setY((screenBounds.getHeight() - MAP_HEIGHT) / 2);

        // 窗口显示
        // 为了初始化窗口不会再闪一下调整大小,第一次窗口显示改到登录页
        // primaryStage.show();

        // 初始化场景
        if (!r) {
            r = true;
            primaryStage.initStyle(TRANSPARENT);
            primaryStage.getIcons().add(new Image("icon.png"));
            new Login();
        } else {
            new Cover();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Scene switchScene(BorderPane pane) {
        Scene sceneCover = new Scene(pane, MAP_WEIGHT, MAP_HEIGHT);
        sceneCover.getStylesheets().add("start.css");
        sceneCover.getRoot().setStyle("-fx-background-color: #000");
        mainStage.setScene(sceneCover);
        return sceneCover;
    }
}
