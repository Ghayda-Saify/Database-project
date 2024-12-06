package com.example.projectfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class main extends Application {
    //    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Dashboard.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1200, 750);
//        //stage.initStyle(StageStyle.UNDECORATED);
//        stage.setScene(scene);
//        stage.show();
//    }
    @Override
    public void start(Stage Primarystage) throws IOException {
        // stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 781, 600);
        Primarystage.setScene(scene);
        Primarystage.setResizable(false);

        Primarystage.show();

        Primarystage.setOnShown(event -> openSecondInterface());

    }
    private void openSecondInterface() {
        try {
            // Load and show the second interface
            Parent secondRoot = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage secondStage = new Stage();
            secondStage.setTitle("login");
            secondStage.setScene(new Scene(secondRoot));
            secondStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}