package com.linyuanlin.LibraryManagement.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Borrow {
    private String uuid;
    private String cardNumber;
    private String bookNumber;
    private Date borrowDate;
    private Date returnDate;

    public Borrow(ResultSet rs) throws SQLException {
        this.uuid = rs.getString("uuid");
        this.cardNumber = rs.getString("cno");
        this.bookNumber = rs.getString("bno");
        this.borrowDate = rs.getDate("borrow_date");
        this.returnDate = rs.getDate("return_date");
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
