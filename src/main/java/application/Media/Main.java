package application.Media;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) throws Exception {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        PlayApplication song = new PlayApplication(new String[]{"../悠闲.mp3","../悠闲.mp3"});
        song.pause();
        song.next();
        song.next();
        song.stop();
    }
}

