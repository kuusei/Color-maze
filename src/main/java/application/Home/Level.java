package application.Home;

import application.Home.Util.MyButton;
import application.Home.Util.MyView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

import static application.Config.*;

class Level extends BorderPane {

    Level() {
        Group group = new Group();

        if (mediaView == null)
            mediaView = new MyView(coverVideo, this);
        group.getChildren().add(mediaView);

        // 按钮列表
        List<Button> Levels = new ArrayList<>();
        // 储存关卡名称的一部分，用于拼接字符串
        String[] strings = {"1", "2", "3", "4", "5", "6"};
        for (int i = 1; i <= strings.length; i++) {
            // 初始化按钮
            MyButton Level = new MyButton(strings[i - 1], 500 + (i - 1) % 3 * 200, ((int) ((i - 1) / 3) + 1) * 150, 200, 300);

            String s = strings[i - 1];
            // 绑定按钮点击事件
            Level.setOnAction(e -> {
                // 修改全局游戏关卡变量
                nowLevel = Integer.parseInt(s) + 1;
                // 结束视频播放器线程
                mediaView.stop();
                mediaView = null;
                mainStage.setScene(null);
                // 启动游戏主程序
                new application.Game.Main().start(mainStage);
            });
            // 将按钮绑定在面板上
            group.getChildren().addAll(Level);
            // 列表添加按钮
            Levels.add(Level);
        }

        Button back = new MyButton("", 10, 10, 55, 40, "re-button");
        group.getChildren().addAll(back);
        mainStage.setScene(new Scene(group, MAP_WEIGHT, MAP_HEIGHT));

        this.getChildren().addAll(mediaView, back);
        for (Button level : Levels)
            this.getChildren().addAll(level);
        Main.switchScene(this);

        //按钮设置动作事件
        back.setOnAction(e -> {
            new Cover();
        });
    }
}
