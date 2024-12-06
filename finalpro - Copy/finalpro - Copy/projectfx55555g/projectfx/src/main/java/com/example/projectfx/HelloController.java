package com.example.projectfx;
import java.sql.DriverManager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


public class HelloController {

    MediaPlayer mediaPlayer;
    @FXML
    private Label Alandalus;
    @FXML
    private AnchorPane splash;

    public void closeApplication() {
        Platform.exit();  // Safely exit the application
        // OR, if Platform.exit() doesn't work, you can use System.exit(0);
    }
    public void initialize() {
        //fadeOutAndGoToNextScreen();
        loadTypingSound();
        startLoadingAnimation(Alandalus.getText());
    }
    private void loadTypingSound() {
        // Load the typing sound file
        String path = "G:\\Uni\\Final database project\\finalpro - Copy\\finalpro - Copy\\projectfx55555g\\projectfx\\src\\main\\resources\\com\\example\\projectfx\\typing-sound2.mp3" ; // Adjust the path as needed
        Media sound = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
    }
    private void startLoadingAnimation(String loadingText) {
        Timeline timeline = new Timeline();

        for (int i = 1; i <= loadingText.length(); i++) {
            final int count = i;
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(i * 200), event -> {
                        Alandalus.setText(loadingText.substring(0, count));
                        // Play the typing sound
                        mediaPlayer.play();
                    })
            );
        }

        // Add a final key frame to stop the sound after the last character is displayed
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis((loadingText.length() + 1) * 200), event -> {
            mediaPlayer.stop(); // Stop the typing sound
        }));
        timeline.setCycleCount(1); // Run once
        timeline.play();
        timeline.setOnFinished(event ->{
            try {
                switchToSecondary();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } );

    }
    public void fadeOutAndGoToNextScreen() {
        // Create a fade transition for each label
        FadeTransition fade1 = createFadeTransition(Alandalus);


        // Play each transition in sequence, so they all fade together
        fade1.play();


        // After the last fade transition finishes, switch to the next screen
        fade1.setOnFinished(event -> goToNextScreen());

        fade1.setOnFinished(event ->{
            try {
                switchToSecondary();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } );

    }

    // Helper method to create a fade transition for a node
    private FadeTransition createFadeTransition(Node node) {
        Alandalus.setOpacity(1.0);

        FadeTransition fade = new FadeTransition(Duration.seconds(2), node);

        fade.setFromValue(1.0); // Fully opaque
        fade.setToValue(0.0);   // Fully transparent
        fade.setCycleCount(2);// ذهاب وإياب (اختفاء وعودة للظهور)
        fade.setAutoReverse(true);   // تمكين الحركة العكسية للعودة للظهور
        fade.play();

        return fade;
    }

    // Method to load the next screen
    private void goToNextScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login"));
            Parent nextRoot = FXMLLoader.load(getClass().getResource("login.fxml"));

            // Create a new scene with the specified root and size
            Scene nextScene = new Scene(nextRoot, 781, 600);

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) splash.getScene().getWindow();

            currentStage.setScene(nextScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void switchToSecondary() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent secondaryRoot = loader.load();

        // Get the current stage and replace the scene
        Stage currentStage = (Stage) splash.getScene().getWindow();
        currentStage.setScene(new Scene(secondaryRoot, 781, 600));
    }


}

//public class HelloController {
//    @FXML
//    private Label Alandalus;
//
//    @FXML
//    protected void onHelloButtonClick() {
//      //  DriverManager.registerDriver();
//        Alandalus.setText("Welcome to JavaFX Application!");
//    }
//    public void onButtonClick() {
//        Alandalus.setText("Welcome to JavaFX Application!");
//    }
//
//}

