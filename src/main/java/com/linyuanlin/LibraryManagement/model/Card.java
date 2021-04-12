package com.linyuanlin.LibraryManagement.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Card {
    private String cardNumber;
    private String name;
    private String department;
    private char type;

    public Card() {
    }

    public Card(ResultSet rs) throws SQLException {
        this.cardNumber = rs.getString("cno");
        this.name = rs.getString("name");
        this.department = rs.getString("department");
        this.type = rs.getString("type").charAt(0);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

}
