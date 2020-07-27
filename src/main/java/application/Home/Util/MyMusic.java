package application.Home.Util;

import application.Media.PlayApplication;

import static application.Config.MAP_HEIGHT;
import static application.Config.MAP_WEIGHT;

/**
 * 全局音乐控件
 *
 * @version 1.1
 */
public class MyMusic extends MyButton {
    // 储存音乐播放线程
    private PlayApplication music = null;
    // 储存当前播放歌曲
    private int now = 1;

    public MyMusic(String[] song) {
        // 定义控件位置和样式
        super("⏸", MAP_WEIGHT * 0.02 + 50, MAP_HEIGHT * 0.01 - 5, 50, 48, "game-button-sm");
        this.setStyle("-fx-font-family: 'Segoe UI Black'");
        // 初始化音乐播放
        music = new PlayApplication(song);
        final String[] songs = song;

        // 绑定点击事件
        this.setOnAction(e -> {
            // 根据不同的播放状态切换
            if (now == 0) {
                now = 1;
                // 修改控件样式，下同
                this.setLayoutY(MAP_HEIGHT * 0.01 - 5);
                this.setText("⏸");
                this.setStyle("-fx-font-family: 'Segoe UI Black'");
                // 重启播放线程
                music = new PlayApplication(songs);
            } else if (now == 1) {
                now = 0;
                this.setLayoutY(MAP_HEIGHT * 0.01);
                this.setText("▶");
                this.setStyle("-fx-font-family:'OCR A Extended'");
                // 关闭播放线程
                music.stop();
                music = null;
            }
        });
    }

    public PlayApplication getMusic() {
        return music;
    }
}
