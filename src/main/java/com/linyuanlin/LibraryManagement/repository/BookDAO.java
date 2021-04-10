package com.linyuanlin.LibraryManagement.repository;


import com.linyuanlin.LibraryManagement.model.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class BookDAO {
    final Connection dataSource;

    public BookDAO(Connection dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Book> getOneByBookNumber(String bookNumber) {
        try {
            Statement stmt = dataSource.createStatement();
            String sql = "SELECT * FROM book WHERE bno='" + bookNumber + "'";
            ResultSet rs = stmt.executeQuery(sql);

            Book book;
            if (rs.next()) {
                book = new Book(rs);
            } else {
                return Optional.empty();
            }

            rs.close();
            stmt.close();
            return Optional.of(book);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    public Book insertNewBook(Book book) {
        try {
            Statement stmt = dataSource.createStatement();
            String sql;

            sql = "INSERT INTO book value (" +
                    book.getBoolNumber() + "," + book.getCategory() + "," +
                    book.getTitle() + "," + book.getPress() + "," +
                    book.getYear() + "," + book.getAuthor() + "," +
                    book.getPrice() + "," + book.getTotal() + "." + book.getStock() + ")";

            ResultSet rs = stmt.executeQuery(sql);
            rs.close();
            stmt.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return book;
    }
}
