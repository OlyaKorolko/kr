package com.files;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Product {
    private int number;
    private String name;
    private LocalDate dateOfArrival;
    private Map<String, Double> categoryAndFrequencyOfSearch;

    Product(int number, String name, LocalDate dateOfArrival, String category, double frequencyOfSearch) {
        this.number = number;
        this.name = name;
        this.dateOfArrival = dateOfArrival;
        this.categoryAndFrequencyOfSearch = new HashMap<>();
        categoryAndFrequencyOfSearch.put(category, frequencyOfSearch);
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

    public Map<String, Double> getCategoryAndFrequencyOfSearch() {
        return categoryAndFrequencyOfSearch;
    }

    public void setCategoryAndFrequencyOfSearch(Map<String, Double> categoryAndFrequencyOfSearch) {
        this.categoryAndFrequencyOfSearch = categoryAndFrequencyOfSearch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return number == product.number &&
                Objects.equals(name, product.name) &&
                Objects.equals(dateOfArrival, product.dateOfArrival) &&
                Objects.equals(categoryAndFrequencyOfSearch, product.categoryAndFrequencyOfSearch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, name, dateOfArrival, categoryAndFrequencyOfSearch);
    }

    @Override
    public String toString() {
        return "[ " +
                "number = " + number +
                ", name = '" + name + '\'' +
                ", dateOfArrival = " + dateOfArrival +
                ", categoryAndFrequencyOfSearch = " + categoryAndFrequencyOfSearch +
                " ]";
    }
}
