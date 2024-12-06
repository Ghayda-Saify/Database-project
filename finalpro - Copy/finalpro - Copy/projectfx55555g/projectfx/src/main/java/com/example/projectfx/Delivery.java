package com.example.projectfx;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Delivery {

    private final SimpleIntegerProperty deliveryId;
    private final SimpleStringProperty deliveryName;
    private final SimpleStringProperty address;
    private final SimpleStringProperty email;
    private final SimpleStringProperty phoneNumber;

    public Delivery(int deliveryId, String deliveryName, String address, String email, String phoneNumber) {
        this.deliveryId = new SimpleIntegerProperty(deliveryId);
        this.deliveryName = new SimpleStringProperty(deliveryName);
        this.address = new SimpleStringProperty(address);
        this.email = new SimpleStringProperty(email);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    public SimpleIntegerProperty deliveryIdProperty() {return deliveryId;}

    public SimpleStringProperty deliveryNameProperty() {return deliveryName;}

    public SimpleStringProperty addressProperty() {return address;}

    public SimpleStringProperty emailProperty() {return email;}

    public SimpleStringProperty phoneNumberProperty() {return phoneNumber;}
}

