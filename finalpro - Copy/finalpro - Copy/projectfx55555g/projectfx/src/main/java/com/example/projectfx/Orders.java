package com.example.projectfx;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Orders {
    private final SimpleIntegerProperty orderID;         // Unique identifier for the order
    private final SimpleIntegerProperty customerID;      // Identifier for the customer
    private final SimpleIntegerProperty deliveryID;      // Identifier for the delivery method
    private final SimpleIntegerProperty employeeID;      // Identifier for the employee who processed the order
    private final SimpleObjectProperty<LocalDate> orderDate;        // The date when the order was placed
    private final SimpleObjectProperty<LocalDate> requiredDate;     // The date when the order is required to be delivered
    private final SimpleObjectProperty<LocalDate> shippedDate;      // The date when the order was shipped
    private final SimpleBooleanProperty check;

    public Orders(boolean check,int orderID, int customerID, int deliveryID, int employeeID,  LocalDate orderDate,  LocalDate requiredDate,  LocalDate shippedDate) {
        this.orderID = new SimpleIntegerProperty(orderID);
        this.customerID = new SimpleIntegerProperty(customerID);
        this.deliveryID = new SimpleIntegerProperty(deliveryID);
        this.employeeID = new SimpleIntegerProperty(employeeID);
        this.orderDate = new SimpleObjectProperty<> (orderDate);
        this.requiredDate = new SimpleObjectProperty<> (requiredDate);
        this.shippedDate = new SimpleObjectProperty<> (shippedDate);
        this.check = new SimpleBooleanProperty(check);
    }

    // Getters for properties
    public SimpleIntegerProperty orderIDProperty() { return orderID; }
    public SimpleIntegerProperty customerIDProperty() { return customerID; }
    public SimpleIntegerProperty deliveryIDProperty() { return deliveryID; }
    public SimpleIntegerProperty employeeIDProperty() { return employeeID; }
    public SimpleObjectProperty<LocalDate> orderDateProperty() { return orderDate; }
    public SimpleObjectProperty<LocalDate> requiredDateProperty() { return requiredDate; }
    public SimpleObjectProperty<LocalDate> shippedDateProperty() { return shippedDate; }
    public SimpleBooleanProperty selectedProperty() { return check; }

    public boolean getselected() {
        return selectedProperty().get();
    }

    public void setIsDelivered(boolean isDelivered) {
        this.selectedProperty().set(isDelivered);
    }

}
