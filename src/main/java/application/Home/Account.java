package application.Home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import static application.Config.*;

public class Account extends BorderPane {
    Account(){
        Group group = new Group();
        //用户名标签及文本框
        Label userName = new Label("用户名:");
        userName.setLayoutX(MAP_WEIGHT * 2 / 5.);
        userName.setLayoutY(MAP_HEIGHT * 5 / 18.);
        TextField user= new TextField();
        user.setLayoutX(MAP_WEIGHT * 9 / 20.);
        user.setLayoutY((MAP_HEIGHT - 10) * 5 / 18.);

        //密码标签及文本框
        Label password = new Label("密码:");
        password.setLayoutX(MAP_WEIGHT * 2 / 5.);
        password.setLayoutY(MAP_HEIGHT * 7 / 18.);
        PasswordField pass = new PasswordField();
        pass.setLayoutX(MAP_WEIGHT * 9 / 20.);
        pass.setLayoutY((MAP_HEIGHT - 10) * 7 / 18.);

        //登录按钮
        Button login = new Button("登录");
        login.setPrefSize(130, 60);
        login.setLayoutX((MAP_WEIGHT + 130) * 2 / 5.);
        login.setLayoutY((MAP_HEIGHT) * 3 / 5.);
        login.getStyleClass().addAll("game-choice");

        group.getChildren().addAll(login,userName,password,user,pass);
        Scene scene = new Scene(group, MAP_WEIGHT, MAP_HEIGHT);
        mainStage.setScene(scene);
        this.getChildren().addAll(login,userName,password,user,pass);
        this.getStyleClass().addAll("game-root_level");
        Scene sceneCover = new Scene(this, MAP_WEIGHT, MAP_HEIGHT);
        sceneCover.getStylesheets().add("start.css");
        mainStage.setScene(sceneCover);

        String userdata = user.getText();
        String passdata = pass.getText();


        //登录按钮点击事件
        login.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent arg0) {
                try {
//                    Cover cover = new Cover();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
