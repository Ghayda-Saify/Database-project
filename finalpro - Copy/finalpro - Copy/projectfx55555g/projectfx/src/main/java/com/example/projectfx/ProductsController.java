package com.example.projectfx;
import com.example.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductsController {
    @FXML
    private Button Customerbtn;

    @FXML
    private Button Homebtn;

    @FXML
    private Button Ordersbtn;

    @FXML
    private Button productbtn;

    @FXML
    private Button Employeebtn;

    @FXML
    private Button deliverybtn;

    @FXML
    private Button signOutbtn;

    @FXML
    private TableView<Product> ProductsTable;

    @FXML
    private TableColumn<Product, Integer> StockQuantitycol;

    @FXML
    private TableColumn<Product, Double> UnitPricecol;

    @FXML
    private TableColumn<Product, LocalDate> ProductDatecol;

    @FXML
    private TableColumn<Product, Integer> ProductIDcol;

    @FXML
    private TableColumn<Product, String> ProductNamecol;

    @FXML
    private TableColumn<Product, String> Designcol;

    @FXML
    private TableColumn<Product,String > Categorycol;

    @FXML
    private String jobPosition;

    @FXML
    private Label welcomeLabel;

    @FXML
    private TextField idtext;
    @FXML
    private TextField nametext;
    @FXML
    private DatePicker datetext;

    @FXML
    private TextField pricetext;
    @FXML
    private TextField quantitytext;
    @FXML
    private TextField designtext;
    @FXML
    private TextField categorytext;

    private final String[] searchele = {"ID","Name","Price"};
    @FXML
    private ComboBox<String> searchby;
    @FXML
    private TextField searchbox;


    @FXML
    public void initialize() {
        // Initialize table columns
        ProductIDcol.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        ProductNamecol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        UnitPricecol.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        StockQuantitycol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        ProductDatecol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        Designcol.setCellValueFactory(cellData -> cellData.getValue().designProperty());
        Categorycol.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());

        // Load product data from the database
        String sql = "SELECT \"Product ID\", \"Product Name\", \"Unit Price\", \"Stock Quantity\", \"Product Date\", \"Design\", \"Category\" FROM \"Product\"";
        loadProductData(sql);
    }

    private void loadProductData(String sql) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "123456";


        ObservableList<Product> productList = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int productId = resultSet.getInt("Product ID");
                String productName = resultSet.getString("Product Name");
                double unitPrice = resultSet.getDouble("Unit Price");
                int stockQuantity = resultSet.getInt("Stock Quantity");
                LocalDate productDate = LocalDate.parse(resultSet.getString("Product Date"));
                String design = resultSet.getString("Design");
                String category = resultSet.getString("Category");

                // Create a new Products object and add it to the list
                productList.add(new Product(productId, productName, unitPrice, stockQuantity, productDate, design, category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
            alert.showAndWait();
        }

        ProductsTable.setItems(productList);
    }
    @FXML
    public void HomebtnOnAction(ActionEvent event) {
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
//    public void initializeWithUserData(String username,String jobPosition) {
//
//        setJobPosition(jobPosition);
//        welcomeLabel.setText( username);
//
//    }
    @FXML
    public void EmployeebtnOnAction(ActionEvent event) {
        jobPosition = UserSession.getInstance().getJobPosition();
        String fxmlFile;

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
            stage.setScene(new Scene(root));stage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace(); // Handle any file loading errors
        }
    }



    @FXML
    public void OrdersbtnOnAction(ActionEvent event) {
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


    public void addOnAction(ActionEvent event) {
        try{
            String getMaxIdQuery = "SELECT MAX(\"Product ID\") FROM \"Product\" ;";
            Statement stmt = connectDB.createStatement();
            ResultSet rs = stmt.executeQuery(getMaxIdQuery);

            int newId = 1;
            if (rs.next()) {
                newId = rs.getInt(1) + 1; // increment max ID by 1
            }

            String sql = "insert into \"Product\" values(?,?,?,?,?,?,?) ";
            PreparedStatement prepare = connectDB.prepareStatement(sql);

            if(!idtext.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("ID is a default value, You can't enter random values");
                alert.showAndWait();
            }

            if(nametext.getText().isEmpty() || datetext.getValue() == null ||pricetext.getText().isEmpty()
                    || designtext.getText().isEmpty() || categorytext.getText().isEmpty() || quantitytext.getText().isEmpty())
            {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the fields");
                alert.showAndWait();

            }else {
                prepare.setInt(1, newId);
                prepare.setString(2, nametext.getText());
                prepare.setDate(4, Date.valueOf(datetext.getValue()));
                prepare.setDouble(6, Double.parseDouble(pricetext.getText()));
                prepare.setString(3, designtext.getText());
                prepare.setString(5, categorytext.getText());
                prepare.setInt(7, Integer.parseInt(quantitytext.getText()));

                prepare.executeUpdate();
                initialize();

            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }//end of add button

    public void clearOnAciot(){
        idtext.clear();
        nametext.clear();
        datetext.setValue(null);
        pricetext.clear();
        designtext.clear();
        categorytext.clear();
        quantitytext.clear();
    }

    //for search by box
    public void searchbyOnAction(){
        List<String> list = new ArrayList<String>(Arrays.asList(searchele));
        ObservableList<String> datalist = FXCollections.observableArrayList(list);
        searchby.setItems(datalist);
    }



    public void search() {
        String search_by = searchby.getSelectionModel().getSelectedItem(); //to know the search will be by which attribute


        if (search_by.equals("ID")) {
            if(searchbox.getText().isEmpty()){
                String sql = "SELECT \"Product ID\", \"Product Name\", \"Unit Price\", \"Stock Quantity\", \"Product Date\", \"Design\", \"Category\" FROM \"Product\" ";
                // Load customer data from the database
                loadProductData(sql);
            }else {
                try {

                    String sql = "SELECT \"Product ID\", \"Product Name\", \"Unit Price\", \"Stock Quantity\", \"Product Date\", \"Design\", \"Category\" FROM \"Product\" where \"Product ID\" = "
                            + Integer.parseInt(searchbox.getText()) + ";";
                    loadProductData(sql);

                } catch (NumberFormatException e) { //if the user enters a non valid value
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("Not Found");
                    alert.showAndWait();
                }
            }
        }

        if (search_by.equals("Name")) {
            if(searchbox.getText().isEmpty()){
                String sql = "SELECT \"Product ID\", \"Product Name\", \"Unit Price\", \"Stock Quantity\", \"Product Date\", \"Design\", \"Category\" FROM \"Product\" ";
                // Load customer data from the database
                loadProductData(sql);
            }else {
                try {

                    String sql = "SELECT \"Product ID\", \"Product Name\", \"Unit Price\", \"Stock Quantity\"," +
                            " \"Product Date\", \"Design\", \"Category\" FROM \"Product\" " +
                            "where \"Product Name\" ='" + searchbox.getText() + "';";
                    loadProductData(sql);

                } catch (NullPointerException e) { //if the user enters a non valid value
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("Not Found");
                    alert.showAndWait();
                }
            }

        }
        if (search_by.equals("Price")) {
            if(searchbox.getText().isEmpty()){
                String sql = "SELECT \"Product ID\", \"Product Name\", \"Unit Price\", \"Stock Quantity\", \"Product Date\", \"Design\", \"Category\" FROM \"Product\" ";
                // Load customer data from the database
                loadProductData(sql);
            }else {
                try {
                    String sql = "SELECT \"Product ID\", \"Product Name\", \"Unit Price\", \"Stock Quantity\"," +
                            " \"Product Date\", \"Design\", \"Category\" FROM \"Product\" " +
                            "where \"Unit Price\" =" + Integer.parseInt(searchbox.getText()) + ";";                // Load customer data from the database
                    loadProductData(sql);
                } catch (NullPointerException e) { //if the user enters a non valid value
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("Not Found");
                    alert.showAndWait();
                }

            }
        }
    }//end of search method





    private int num;
    public void select (){
        Product cust = ProductsTable.getSelectionModel().getSelectedItem();
        num = ProductsTable.getSelectionModel().getSelectedIndex();
        if(num < 0)
            return;

        idtext.setText(String.valueOf(cust.idProperty().get()));
        nametext.setText(String.valueOf(cust.nameProperty().get()));
        pricetext.setText(String.valueOf(cust.priceProperty().get()));
        quantitytext.setText(String.valueOf(cust.quantityProperty().get()));
        designtext.setText(String.valueOf(cust.designProperty().get()));
        categorytext.setText(String.valueOf(cust.categoryProperty().get()));
        datetext.setValue(cust.dateProperty().get());
    }
    public void deleteOnAciot(){


        try{
            String sql = "DELETE FROM \"Product\" WHERE \"Product ID\" = " +idtext.getText()+";";
            Statement statment = connectDB.createStatement();
            statment.executeUpdate(sql);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully deleted");
            alert.showAndWait();
            sql = "SELECT \"Product ID\", \"Product Name\", \"Unit Price\", \"Stock Quantity\", \"Product Date\", \"Design\", \"Category\" FROM \"Product\"";

            loadProductData(sql);



        }catch(Exception e){

            e.printStackTrace();
            e.getCause();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Failure");
            alert.setHeaderText(null);
            alert.setContentText("failed to delete");
            alert.showAndWait();
        }

    }


    public void updateOnAction(){

        try {
            int enteredId = Integer.parseInt(idtext.getText());

            // Get the selected employee from the table
            Product selectedEmployee = ProductsTable.getSelectionModel().getSelectedItem();

            if (selectedEmployee == null || selectedEmployee.idProperty().get() != enteredId) {
                // Display an alert if the IDs don't match
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid ID");
                alert.setHeaderText(null);
                alert.setContentText("ID is invalid, please enter the correct ID.");
                alert.showAndWait();
            }
            else {
                try{
                    String sql = "update \"Product\" set   \"Product Name\" = '" +nametext.getText()
                            +"', \"Unit Price\" = "+pricetext.getText()+", \"Stock Quantity\" = "
                            + quantitytext.getText() +"   , \"Product Date\" = '"
                            +datetext.getValue()+  "', \"Design\"  = '"+ designtext.getText()
                            +"' , \"Category\" = '"+ categorytext.getText()
                            +"'  where \"Product ID\" = "+ idtext.getText()+";";
                    Statement statment = connectDB.createStatement();
                    statment.executeUpdate(sql);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully updated");
                    alert.showAndWait();

                    sql = "SELECT \"Product ID\", \"Product Name\", \"Unit Price\", \"Stock Quantity\", \"Product Date\", \"Design\", \"Category\" FROM \"Product\"";

                    loadProductData(sql);



                }catch(Exception e){

                    e.printStackTrace();
                    e.getCause();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Failure");
                    alert.setHeaderText(null);
                    alert.setContentText("Enter Valid information");
                    alert.showAndWait();
                    String sql = "SELECT \"Product ID\", \"Product Name\", \"Unit Price\", \"Stock Quantity\", \"Product Date\", \"Design\", \"Category\" FROM \"Product\"";
                    loadProductData(sql);
                }

            }
        } catch (NumberFormatException e) {
            // Show an alert if the entered text is not a valid integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid numeric ID.");
            alert.showAndWait();
            String sql = "SELECT \"Product ID\", \"Product Name\", \"Unit Price\", \"Stock Quantity\", \"Product Date\", \"Design\", \"Category\" FROM \"Product\"";

            loadProductData(sql);
        }

    }


}
