package com.files;

import java.time.LocalDate;
import java.util.Objects;

public class Product {
    private int number;
    private String name;
    private LocalDate dateOfArrival;
    private String category;
    private double frequencyOfSearch;

    public double getFrequencyOfSearch() {
        return frequencyOfSearch;
    }

    public void setFrequencyOfSearch(double frequencyOfSearch) {
        this.frequencyOfSearch = frequencyOfSearch;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfArrival() {
        return dateOfArrival;
    }

    public void setDateOfArrival(LocalDate dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    Product(int number, String name, LocalDate dateOfArrival, String category, double frequencyOfSearch) {
        this.number = number;
        this.name = name;
        this.dateOfArrival = dateOfArrival;
        this.category = category;
        this.frequencyOfSearch = frequencyOfSearch;
    }

    Product() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return number == product.number &&
                Objects.equals(name, product.name) &&
                Objects.equals(dateOfArrival, product.dateOfArrival) &&
                Objects.equals(category, product.category) &&
                Objects.equals(frequencyOfSearch, product.frequencyOfSearch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, name, dateOfArrival, category, frequencyOfSearch);
    }

    @Override
    public String toString() {
        return number + " " + name + " " + dateOfArrival + " " + category + " " + frequencyOfSearch + " ";
    }
}
