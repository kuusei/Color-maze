package application.Home;

import application.Home.Util.MyButton;
import application.Home.Util.MyMusic;
import application.Home.Util.MyView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import static application.Config.*;

public class Story extends BorderPane {
    String[][] top = {
        {"（你漂浮在黑暗的宇宙中，飞船内部，仿若从无尽岁月的沉眠中醒来。）\n(请点击文字)", " 你：我……我在哪？头好痛啊。\n" +
            "（你从地板上撑着坐起）", "飞船AI：……醒了吗？你已经昏迷很久很久了。", "你：究竟发生了什么？",
            "飞船AI：传送门发生了未知的爆炸。你或许是这次爆炸的唯一幸存者。你的当务之急是先熟悉飞船操纵系统。", "Tip：通过鼠标悬停控制飞船行驶，走出此地。"},
        {"你：这儿究竟是什么鬼地方？！", "飞船AI：……据我多年观测，这是一个奇异的空间。空间内遍布着各种颜色的射线，它们阻止着异界物质的通行。你可以试着吸收同色物质同化飞船结构，通过同类射线屏障……", "你：……"},
        {"飞船AI：你也发现了吧！有些射线可以通过拾取对应的钥匙进行解锁。试着向更深处进发吧。", " 你：……"},
        {"你：你究竟还知道些什么？我想回去！", "飞船AI：……传送门爆炸时，我的核心接收到了一个奇异的电磁信号，解析发现了一组星际坐标，系统判断这或许是唯一的生路……如你所见，就是此地。强行启动星际跃迁的代价是你陷入了长时间的昏迷。"},
        {"你：我现在该往哪儿前进呢？", "飞船AI：……进入这个空间时我在最深处感受到了一股生命波动，或许那里会有你想要的答案……"},
        {"飞船AI：这儿有些奇怪……或许你可以借助这些天然虫洞实现短距离空间跳跃？或许能离目的地更进一步……"},
        {"飞船AI：信号越来越强烈了……或许这是最后一道关卡了，你感受到了吗？", "你：这个信号……？！难道是？……"},
        {"（最后一道传送门通往了空间深处）", "你好，空间探索者，这个空间是为了抵抗异族入侵而开辟的“圣所”，500年前，第一批进行大规模星际探索的人类幸存者进入了这道天然的屏障。接下来的道路并不会一帆风顺。准备好迎接新的挑战了吗？"}};

    public Story() {
        Group group = new Group();

        // 视频部分
        if (mediaView == null) {
            System.out.println("mediaView = " + mediaView);
            mediaView = new MyView(coverVideo, this);
        }
        if (myMusic == null) {
            myMusic = new MyMusic(SONGS);
        }

        // 开始游戏按钮
        Button talk = new MyButton("", (MAP_WEIGHT - 1200) / 2, (MAP_HEIGHT) * 9 / 14, 1200, 400, "game-button-sm");
        talk.setStyle("-fx-font-family: '微软雅黑'");
        talk.setWrapText(true);
        getFrame(talk, 0);

        group.getChildren().addAll(mediaView, talk, myMusic);
        mainStage.setScene(new Scene(group, MAP_WEIGHT, MAP_HEIGHT));

        this.getChildren().addAll(mediaView, talk, myMusic);
        // 场景切换函数
        Main.switchScene(this);
    }

    void getFrame(Button t, int i) {
        IntegerProperty charCount = new SimpleIntegerProperty();
        KeyFrame startFrame = new KeyFrame(Duration.ZERO, new KeyValue(
            charCount, 0));
        KeyFrame endFrame = new KeyFrame(Duration.seconds(top[nowLevel - 1][i].length() / 30.), new KeyValue(
            charCount, top[nowLevel - 1][i].length()));

        final int Finali = i;
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(startFrame, endFrame);
        charCount.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                try {
                    String textToDisplay = top[nowLevel - 1][Finali].substring(0, newValue.intValue());
                    t.setText(textToDisplay);
                } catch (Exception ignored){}
                t.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent arg0) {
                        try {
                            getFrame(t, Finali + 1);
                        } catch (Exception e) {
                            if (nowLevel < 7) {
                                mediaView.stop();
                                mediaView = null;
                                mainStage.setScene(null);
                                new application.Game.Main().start(mainStage);
                            } else {
                                new application.Home.Main().start(mainStage);
                            }
                        }
                    }
                });
            }
        });
        timeline.playFromStart();
    }
}
