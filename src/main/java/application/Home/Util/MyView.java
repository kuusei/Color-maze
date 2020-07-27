package application.Home.Util;

import application.Media.PlayApplication;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import static application.Config.src;

public class MyView extends MediaView {
    private MediaPlayer player;
    public MyView(String file, BorderPane pane) {
        super();
        player = new PlayApplication(new String[]{src + file}).getMediaPlayer();
        // 设置重复播放
        player.setCycleCount(MediaPlayer.INDEFINITE);
        // 创建播放控件并设置大小
        this.setMediaPlayer(player);
        this.fitHeightProperty().bind(pane.heightProperty());
        this.fitWidthProperty().bind(pane.widthProperty());
    }

    public void stop() {
        player.stop();
    }
}
