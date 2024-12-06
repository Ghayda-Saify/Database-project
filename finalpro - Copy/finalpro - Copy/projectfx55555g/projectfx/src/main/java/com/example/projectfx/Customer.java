package com.example.projectfx;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {

    private final SimpleIntegerProperty customerId;
    private final SimpleStringProperty customerName;
    private final SimpleStringProperty address;
    private final SimpleStringProperty email;
    private final SimpleStringProperty phone;

    public Customer(int customerId, String customerName, String address, String email, String phone) {
        this.customerId = new SimpleIntegerProperty(customerId);
        this.customerName = new SimpleStringProperty(customerName);
        this.address = new SimpleStringProperty(address);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
    }

    public SimpleIntegerProperty customerIdProperty() { return customerId; }

    public SimpleStringProperty customerNameProperty() { return customerName; }

    public SimpleStringProperty addressProperty() { return address; }

    public SimpleStringProperty emailProperty() { return email; }

    public SimpleStringProperty phoneProperty() { return phone; }

}
