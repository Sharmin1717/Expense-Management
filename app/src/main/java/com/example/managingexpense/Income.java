package com.example.managingexpense;  // Use your actual package name here

public class Income {

    private String id;      // Unique identifier for the income
    private double amount;  // The amount of income
    private String note;    // A note or description of the income
    private String date;    // The date when the income was added

    // Default constructor (required for Firebase to deserialize the object)
    public Income() {

    }

    // Constructor to initialize the fields
    public Income(String id, double amount, String note, String date) {
        this.id = id;
        this.amount = amount;
        this.note = note;
        this.date = date;
    }

    // Getter and setter methods for each field

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
