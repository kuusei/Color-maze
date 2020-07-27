package application.Home;

import application.Config;
import application.Home.Util.MyButton;
import application.Home.Util.MyLabel;
import application.Home.Util.MyView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import static application.Config.*;

public class Setting extends BorderPane {
    Setting() {
        Group group = new Group();
        mediaView = new MyView(coverVideo, this);

        Button back = new MyButton("", 10, 10, 55, 40, "re-button");

        //音量滑块
        Slider volume = new Slider(0, 100, 1);
        volume.setLayoutX((MAP_WEIGHT - 100) / 2.);
        volume.setLayoutY((MAP_HEIGHT + 110) / 5.);
        volume.getStyleClass().addAll("slider");

        //音量标签
        Label opacityCaption = new MyLabel("Volume", (MAP_WEIGHT - 300) / 2., MAP_HEIGHT / 5., 100, 60, "setting-text");

        //音量数值
        Label opacityValue = new MyLabel(Double.toString(volume.getValue()), (MAP_WEIGHT + 200) / 2., MAP_HEIGHT / 5., 100, 60, "setting-text");

        //音量初始值
        volume.setValue(60);
        opacityValue.setText(String.format("%.0f", 60.));

        ToggleGroup tg = new ToggleGroup();
        RadioButton plane1 = new RadioButton();
        plane1.setToggleGroup(tg);
        plane1.setLayoutX((MAP_WEIGHT + 300) / 3.);
        plane1.setLayoutY((MAP_HEIGHT + 400) / 5.);
        plane1.setSelected(true);

        ImageView imageView1 = new ImageView();
        imageView1.setImage(new Image("plane/blue1.png"));
        imageView1.setLayoutX((MAP_WEIGHT - 100)  / 2.);
        imageView1.setLayoutY((MAP_HEIGHT + 400) / 5.);

        RadioButton plane2 = new RadioButton();
        plane2.setToggleGroup(tg);
        plane2.setLayoutX((MAP_WEIGHT + 300) / 3.);
        plane2.setLayoutY((MAP_HEIGHT + 400) / 3.);

        ImageView imageView2 = new ImageView();
        imageView2.setImage(new Image("plane/blue2.png"));
        imageView2.setLayoutX((MAP_WEIGHT - 100) / 2.);
        imageView2.setLayoutY((MAP_HEIGHT + 400) / 3.);

        group.getChildren().addAll(mediaView, back, opacityCaption, volume, opacityValue, plane1, plane2, imageView1, imageView2);
        mainStage.setScene(new Scene(group, MAP_WEIGHT, MAP_HEIGHT));

        this.getChildren().addAll(mediaView, back, opacityCaption, volume, opacityValue, plane1, plane2, imageView1, imageView2);
        Main.switchScene(this);

        //滑块监听事件
        volume.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                myMusic.getMusic().setVolume((Double) new_val / 100);
                opacityValue.setText(String.format("%.0f", new_val));
            }
        });

        //单选框监听事件
        tg.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
                    if (tg.getSelectedToggle() == plane1) {
                        Config.plane = "1";
                        System.out.println("plane1");
                    } else {
                        Config.plane = "2";
                    }
        });

        //返回开始游戏界面
        back.setOnAction(e -> {
            new Cover();
        });
    }
}
