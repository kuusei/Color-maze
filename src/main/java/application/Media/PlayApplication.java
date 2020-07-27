package application.Media;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

//播放音乐的线程类
public class PlayApplication extends Application {
    private List<String> songs = new ArrayList<>();
    // 播放状态
    private int now = 0;
    // 正在播放
    private final static int STARTED = 1;
    // 播放器未实例化
    private final static int ISNULL = 0;
    // 非播放状态
    private final static int UNKNOWN = -1;
    // 继续播放控制
    private volatile boolean CONTINUE = true;
    // 音量
    private double volume = 0.6;

    // 播放器
    private MediaPlayer mediaPlayer;
    // 播放锁 不知道干嘛用的
    private final Object playLock = new Object();

    public PlayApplication(String[] songs) {
        this.songs = Arrays.asList(songs);
        play();
        try {
            // 启动播放控制线程
            new Thread(new Runnable() {
                public void run() {
                    // 如果继续播放
                    while (CONTINUE) {
                        try {
                            if (getState() == STARTED) {
                                synchronized (playLock) {
                                    playLock.wait();
                                }
                            }
                            System.out.println("线程唤醒");
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mediaPlayer.stop();
                    mediaPlayer = null;
                }
            }).start();
        } catch (Exception e) {
            try {
                next();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // 播放下一首
    public void next() throws Exception {
        if (true) {
            if (songs.size() <= ++now) {
                now = 0;
            }
            System.out.println("当前播放 = " + now);
            play();
            return;
        }
        CONTINUE = true;
        synchronized (playLock) {
            playLock.notifyAll();
        }
    }

    // 得到播放状态
    public int getState() {
        if (null == mediaPlayer)
            return ISNULL;
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING)
            return STARTED;
        return UNKNOWN;
    }

    // 启动播放
    @Override
    public void start(Stage primaryStage) throws Exception {
        play();
    }

    public static void main(String[] args) {
        launch();
    }

    private void play() {
        File musicFile = new File(songs.get(now));
        Media media = new Media(musicFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(volume);
        mediaPlayer.setAutoPlay(true);
        // 绑定文件结尾时的操作
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                try {
                    next();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 停止播放
    public void stop() {
        CONTINUE = false;
        synchronized (playLock) {
            playLock.notifyAll();
        }
    }

    public boolean pause() {
        if (getState() == STARTED) {
            mediaPlayer.pause();
            return true;
        }
        return false;
    }

    //设置音量
    public void setVolume(double d) {
        volume = d;
        mediaPlayer.setVolume(d);
    }

    // 获取音量
    public double getVolume() {
        return volume;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }
}