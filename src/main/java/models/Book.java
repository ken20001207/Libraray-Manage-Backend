package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Book {
    private String boolNumber;
    private String title;
    private String press;
    private int year;
    private String city;
    private String author;
    private String category;
    private double price;
    private int total;
    private int stock;

    public Book(ResultSet rs) throws SQLException {
        this.boolNumber = rs.getString("bno");
        this.category = rs.getString("category");
        this.title = rs.getString("title");
        this.press = rs.getString("press");
        this.year = rs.getInt("year");
        this.author = rs.getString("author");
        this.price = rs.getDouble("price");
        this.total = rs.getInt("total");
        this.stock = rs.getInt("stock");
    }

    public String getBoolNumber() {
        return boolNumber;
    }

    public void setBoolNumber(String boolNumber) {
        this.boolNumber = boolNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
