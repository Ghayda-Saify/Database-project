package com.example.projectfx;

import com.example.DataBaseConnection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public AnchorPane pane;
    @FXML
    public TextField userNametxt;
    @FXML
    public PasswordField passtxt;
    public Button loginbtn;
    @FXML
    private javafx.scene.layout.BorderPane BorderPane;
    @FXML
    private Label loginfaild;

    private static String jobPosition;


    @FXML
//    @Override
    private Timeline timeline1 = new Timeline(
            new KeyFrame(Duration.ZERO, event -> loginfaild.setVisible(true)), // Show the label
            new KeyFrame(Duration.seconds(1), event -> loginfaild.setVisible(false)) // Hide it after 4 seconds
    );

    public void initialize(URL url, ResourceBundle rb) {
        loginfaild.setTextFill(Color.RED);

        //  org.postgresql.Driver driver = new org.postgresql.Driver(); -- from video https://www.youtube.com/watch?v=DH3dWzmkT5Y
        //  File brandingFile = new File("org.example.demo7"); -- if the project didn't recognize the image
        // brandingImage = new Image(brandingFile.toURI().toString());
        // Set up the timeline to show the label for 4 seconds


    }
    public void loginBtnOnAction(ActionEvent event1) throws IOException {
        if(userNametxt.getText().isEmpty()){
            loginfaild.setText("Please enter your Username");
            loginfaild.setVisible(false); // Initially invisible
            timeline1.setCycleCount(1); // Run only once
            // Start the timeline when you need to show the label
            timeline1.play();
        }
        if(passtxt.getText().isEmpty()) {
            loginfaild.setText("Please enter your Password");
            loginfaild.setVisible(false); // Initially invisible
            timeline1.setCycleCount(1); // Run only once
            // Start the timeline when you need to show the label
            timeline1.play();
        }
        if( !userNametxt.getText().isEmpty() && !passtxt.getText().isEmpty()){
            vaidateLogIn();
        }

    }


    public void vaidateLogIn() {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();
        //System.out.println("Connected to database");

        try {
            String query = "SELECT COUNT(1) FROM \"Employee\" WHERE \"User Name\" = ? COLLATE \"C\" AND \"Password\" = ? COLLATE \"C\"";
            PreparedStatement pstmt = connectDB.prepareStatement(query);
            pstmt.setString(1, userNametxt.getText());
            pstmt.setString(2, passtxt.getText());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt(1) > 0) { // login sucsseded -> go to dashboard
                    jobPosition = setJobPositionForUser(userNametxt.getText());
                    UserSession.getInstance().setJobPosition(jobPosition);
                    UserSession.getInstance().setUsername(userNametxt.getText());
                    UserSession.getInstance().setJobPosition(jobPosition);

                    try {

                        // Load the FXML file for the dashboard
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
                        Parent dashboardRoot = fxmlLoader.load();

//                            DashboardController dashboardController = fxmlLoader.getController();
//                            dashboardController.initializeWithUserData(userNametxt.getText(), jobPosition);
//

                        // Create a new stage and scene for the dashboard
                        Stage dashboardStage = new Stage();
                        dashboardStage.setTitle("Dashboard");
                        dashboardStage.setScene(new Scene(dashboardRoot));
                        dashboardStage.setResizable(false);
                        // Show the new stage (dashboard window)
                        dashboardStage.show();

                        // Close the current window if desired
                        Stage currentStage = (Stage) BorderPane.getScene().getWindow();
                        currentStage.close();

                    } catch (IOException e) {
                        e.printStackTrace(); // Print any errors that occur
                    }



                } else {
                    loginfaild.setTextFill(Color.RED);
                    loginfaild.setText("Login failed"); // Login failed
                    loginfaild.setVisible(false); // Initially invisible
                    timeline1.setCycleCount(1); // Run only once
                    // Start the timeline when you need to show the label
                    timeline1.play();
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }
    public String setJobPositionForUser(String username) {
        String jobPosition2 = null;
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connectDB = connectNow.getConnection();

        String query = "SELECT \"Job Position\" FROM \"Employee\" WHERE \"User Name\" = ?";
        try (PreparedStatement pstmt = connectDB.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                jobPosition2 = rs.getString("Job Position");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Job Position: " + jobPosition2);
        this.jobPosition=jobPosition2;
        return jobPosition2;
    }
//
//    public String getJobPosition()
//    {
//        return jobPosition;
//    }

}
