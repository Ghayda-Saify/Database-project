package com.example.projectfx;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Product {
    private final SimpleIntegerProperty productId;
    private final SimpleStringProperty productName;
    private final SimpleDoubleProperty unitPrice;
    private final SimpleIntegerProperty stockQuantity;
    private final SimpleObjectProperty<LocalDate> productDate;
    private final SimpleStringProperty design;
    private final SimpleStringProperty category;

    public Product(int productId, String productName, double unitPrice, int stockQuantity, LocalDate productDate, String design, String category) {
        this.productId = new SimpleIntegerProperty(productId);
        this.productName = new SimpleStringProperty(productName);
        this.unitPrice = new SimpleDoubleProperty(unitPrice);
        this.stockQuantity = new SimpleIntegerProperty(stockQuantity);
        this.productDate = new SimpleObjectProperty<> (productDate);
        this.design = new SimpleStringProperty(design);
        this.category = new SimpleStringProperty(category);
    }

    public SimpleIntegerProperty idProperty() { return productId; }

    public SimpleStringProperty nameProperty() { return productName; }

    public SimpleDoubleProperty priceProperty() { return unitPrice; }

    public SimpleIntegerProperty quantityProperty() { return stockQuantity;}

    public SimpleObjectProperty<LocalDate> dateProperty() { return productDate; }

    public SimpleStringProperty designProperty() { return design; }

    public SimpleStringProperty categoryProperty() { return category; }

}
