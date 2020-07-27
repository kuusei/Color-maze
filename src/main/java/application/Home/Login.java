package application.Home;

import application.Home.Util.MyButton;
import application.Home.Util.MyMusic;
import application.Home.Util.MyView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import static application.Config.*;

public class Login extends BorderPane {
    Login() {
        Group group = new Group();

        // 视频部分
        mediaView = new MyView(loginVideo, this);
        myMusic = new MyMusic(SONGS);
        // 设置按钮
        Button tourists = new MyButton("play", (MAP_WEIGHT - 130.) / 2, (MAP_HEIGHT - 130.));

        // group层
        group.getChildren().addAll(mediaView, tourists);
        mainStage.setScene(new Scene(group, MAP_WEIGHT, MAP_HEIGHT));
        // 这里补充了之前Main类没show的过程,目的之前讲过
        mainStage.show();

        // BorderPane层(Login)
        // 这句话存在多次复用的情况 考虑优化（已经优化了）
        this.getChildren().addAll(mediaView, tourists);
        Main.switchScene(this);

        // 点击事件
        // 游客登录(即直接进入游戏主界面)
        tourists.setOnAction(e -> {
            new Cover();
        });
    }
}
