package com.example.projectfx;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Employee {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleObjectProperty<LocalDate> birthdate;
    private final SimpleIntegerProperty age;
    private final SimpleStringProperty address;
    private final SimpleStringProperty email;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty username;
    private final SimpleStringProperty password;
    private final SimpleStringProperty jobPosition;
    private final SimpleDoubleProperty salary;
    private final SimpleIntegerProperty workingHours;
    private final SimpleObjectProperty<LocalDate> hireDate;

    public Employee(int id, String name, LocalDate birthDate, int age, String address, String email, String phone, String username, String password,
                    String jobPosition, double salary, int workingHours, LocalDate hireDate) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.birthdate = new SimpleObjectProperty<>(birthDate);
        this.age = new SimpleIntegerProperty(age);
        this.address = new SimpleStringProperty(address);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.jobPosition = new SimpleStringProperty(jobPosition);
        this.salary = new SimpleDoubleProperty(salary);
        this.workingHours = new SimpleIntegerProperty(workingHours);
        this.hireDate = new SimpleObjectProperty<>(hireDate);    }

    public SimpleIntegerProperty idProperty() { return id; }

    public SimpleStringProperty nameProperty() { return name; }
    public SimpleIntegerProperty ageProperty() { return age; }
    public SimpleStringProperty addressProperty() { return address; }
    public SimpleStringProperty emailProperty() { return email; }
    public SimpleStringProperty phoneProperty() { return phone; }
    public SimpleStringProperty usernameProperty() { return username; }
    public SimpleStringProperty passwordProperty() { return password; }
    public SimpleStringProperty jobPositionProperty() { return jobPosition; }
    public SimpleDoubleProperty salaryProperty() { return salary; }
    public SimpleIntegerProperty workingHoursProperty() { return workingHours; }
    public SimpleObjectProperty<LocalDate> hireDateProperty() { return hireDate; }
    public SimpleObjectProperty<LocalDate> birthdateDateProperty() { return birthdate; }

}
