package com.example.projectfx;

import com.example.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardController {
    @FXML
    private Button Homebtn;

    @FXML
    private Button Ordersbtn;

    @FXML
    private Button Customerbtn;

    @FXML
    private Button productbtn;

    @FXML
    private Button Employeebtn;

    @FXML
    private Button deliverybtn;

    @FXML
    private Button signOutbtn;

    @FXML
    private Label welcomeLabel;

    @FXML
    private String jobPosition;
    @FXML
    private AnchorPane custreport;
    @FXML
    private AnchorPane empreport;
    @FXML
    private AnchorPane prodreport;
    @FXML
    private BarChart<String, Number> chart;
    @FXML
    private PieChart piechart;

    MediaPlayer mediaPlayer;

    private void loadbuttonSound() {
        try {
            // Load the sound file path once
            String path = "C:\\Users\\GIGABYTE\\IdeaProjects\\finalpro - Copy\\finalpro - Copy\\projectfx55555g\\projectfx\\src\\main\\resources\\com\\example\\projectfx\\buttonSound.mp3";
            Media sound = new Media(new File(path).toURI().toString());

            // If mediaPlayer is already initialized, stop and reset it before replaying
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }

            // Create a new MediaPlayer instance and play the sound
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void onButtonClicked(ActionEvent event) {
//        loadbuttonSound();
//        // Perform other button actions here
//    }

        private ObservableList<XYChart.Data<String, Number>> getChartDataFromDatabase() {

            ObservableList<XYChart.Data<String, Number>> chartData = FXCollections.observableArrayList();

            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String password = "123456";

            String query = "SELECT EXTRACT(MONTH FROM \"Orders\".\"Order_Date\") AS month, "+
            "(\"Order details\".\"Quantity\" * \"Product\".\"Unit Price\") AS totalprice  "+
           " FROM \"Order details\" JOIN \"Product\" ON \"Order details\".\"FK ProductID\" = \"Product\".\"Product ID\" "+
           " JOIN \"Orders\" ON \"Order details\".\"FK OrderID\" = \"Orders\".\"Order_ID\" "+
           " WHERE EXTRACT(YEAR FROM \"Orders\".\"Order_Date\") = 2024 "+
           " ORDER BY month " ;


            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String month = rs.getString("month");
                    int sales = rs.getInt("totalprice");

                    // Add each data point to the series
                    chartData.add(new XYChart.Data<>(month, sales));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return chartData;
        }
    private void getpieChartDataFromDatabase() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123456")) {
            String sql = "SELECT \"Customer\".\"Address\" as city, COUNT(*) AS customer_count FROM \"Customer\"  GROUP BY city ORDER BY customer_count DESC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            // Populate the pie chart data
            while (rs.next()) {
                String city = rs.getString("city");
                int customerCount = rs.getInt("customer_count");
                pieChartData.add(new PieChart.Data(city, customerCount));
            }
            piechart.setData(pieChartData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



@FXML
    public void initialize() {

    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Monthly Sales");

    // Fetch data from the database and set it to the chart
    series.getData().addAll(getChartDataFromDatabase());

    // Add the series to the chart
    chart.getData().add(series);
    getpieChartDataFromDatabase();

}

    @FXML
    public void HomebtnOnAction(ActionEvent event) {

        System.out.println("Attempting to load Dashboard.fxml");
        try {
            // Load the FXML file for the dashboard
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent dashboardRoot = fxmlLoader.load();

            // Create a new stage and scene for the dashboard
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Dashboard");
            dashboardStage.setScene(new Scene(dashboardRoot));
            dashboardStage.setResizable(false);
            // Show the new stage (dashboard window)
            dashboardStage.show();

            // Close the current window if desired
            Stage currentStage = (Stage) Homebtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print any errors that occur
        }
    }

    //    public void setJobPosition(String jobPosition) {
//        this.jobPosition=jobPosition;
//    }
//    public void initializeWithUserData(String username,String jobPos) {
//
//        setJobPosition(jobPos);
//        welcomeLabel.setText( username);
//
//    }
    @FXML
    public void EmployeebtnOnAction(ActionEvent event) {

        String fxmlFile;
        jobPosition = UserSession.getInstance().getJobPosition();

        // Choose the FXML file based on the job position
        switch (jobPosition.toLowerCase()) {
            case "manager":
                fxmlFile = "Manager.fxml";
                break;
            default:
                fxmlFile = "Employee.fxml"; // A generic or default dashboard for other positions
                break;
        }

        try {
            // Load the selected FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = fxmlLoader.load();

            // Set up the new scene
            Stage stage = (Stage) Employeebtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace(); // Handle any file loading errors
        }
    }

    @FXML
    public void OrdersbtnOnAction(ActionEvent event) {
//        setJobPosition(jobPosition);
        try {
            // Load the FXML file for the dashboard
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Orders.fxml"));
            Parent dashboardRoot = fxmlLoader.load();

            // Create a new stage and scene for the dashboard
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Orders");
            dashboardStage.setScene(new Scene(dashboardRoot));
            dashboardStage.setResizable(false);
            // Show the new stage (dashboard window)
            dashboardStage.show();

            // Close the current window if desired
            Stage currentStage = (Stage) Ordersbtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print any errors that occur
        }

    }
    @FXML
    public void productbtnOnAction(ActionEvent event) {
        try {
            // Load the FXML file for the dashboard
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("products.fxml"));
            Parent dashboardRoot = fxmlLoader.load();

            // Create a new stage and scene for the dashboard
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Orders");
            dashboardStage.setScene(new Scene(dashboardRoot));
            dashboardStage.setResizable(false);
            // Show the new stage (dashboard window)
            dashboardStage.show();

            // Close the current window if desired
            Stage currentStage = (Stage) productbtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print any errors that occur
        }

    }

    public void CustomerbtnOnAction(ActionEvent event){

        try {
            // Load the FXML file for the dashboard
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Customer.fxml"));
            Parent dashboardRoot = fxmlLoader.load();

            // Create a new stage and scene for the dashboard
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Customer");
            dashboardStage.setScene(new Scene(dashboardRoot));
            dashboardStage.setResizable(false);
            // Show the new stage (dashboard window)
            dashboardStage.show();

            // Close the current window if desired
            Stage currentStage = (Stage) Customerbtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print any errors that occur
        }

    }

    public void DelivrybtnOnAction(ActionEvent event){
        try {
            // Load the FXML file for the dashboard
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Delivery.fxml"));
            Parent dashboardRoot = fxmlLoader.load();

            // Create a new stage and scene for the dashboard
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Delivery");
            dashboardStage.setScene(new Scene(dashboardRoot));
            dashboardStage.setResizable(false);
            // Show the new stage (dashboard window)
            dashboardStage.show();

            // Close the current window if desired
            Stage currentStage = (Stage) deliverybtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print any errors that occur
        }

    }
    @FXML
    public void signOutbtnOnAction(ActionEvent event){
        try {
            // Load the FXML file for the dashboard
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent dashboardRoot = fxmlLoader.load();

            // Create a new stage and scene for the dashboard
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("login");
            dashboardStage.setScene(new Scene(dashboardRoot));
            dashboardStage.setResizable(false);
            // Show the new stage (dashboard window)
            dashboardStage.show();

            // Close the current window if desired
            Stage currentStage = (Stage) signOutbtn.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Print any errors that occur
        }
    }


    DataBaseConnection connectNow = new DataBaseConnection();
    Connection connectDB = connectNow.getConnection();

    public void customerreport(){
        try {
            JasperDesign load = JRXmlLoader.load("G:\\Uni\\Final database project\\finalpro - Copy\\finalpro - Copy\\projectfx55555g\\projectfx\\src\\main\\resources\\com\\example\\projectfx\\Customers.jrxml");
            JasperReport jasperreport = JasperCompileManager.compileReport(load);
            JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport,null,connectDB);
            JasperViewer.viewReport(jasperprint,false);
        } catch (Exception e) {
            e.printStackTrace(); // Print any errors that occur
        }
    }
    public void employeereport(){
        try {
            JasperDesign load = JRXmlLoader.load("G:\\Uni\\Final database project\\finalpro - Copy\\finalpro - Copy\\projectfx55555g\\projectfx\\src\\main\\resources\\com\\example\\projectfx\\Employees.jrxml");
            JasperReport jasperreport = JasperCompileManager.compileReport(load);
            JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport,null,connectDB);
            JasperViewer.viewReport(jasperprint,false);
        } catch (Exception e) {
            e.printStackTrace(); // Print any errors that occur
        }
    }
    public void productreport(){
        try {
            JasperDesign load = JRXmlLoader.load("G:\\Uni\\Final database project\\finalpro - Copy\\finalpro - Copy\\projectfx55555g\\projectfx\\src\\main\\resources\\com\\example\\projectfx\\Products.jrxml");
            JasperReport jasperreport = JasperCompileManager.compileReport(load);
            JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport,null,connectDB);
            JasperViewer.viewReport(jasperprint,false);
        } catch (Exception e) {
            e.printStackTrace(); // Print any errors that occur
        }
    }



}