package com.linyuanlin.LibraryManagement.repository;


import com.linyuanlin.LibraryManagement.model.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDAO {
    final Connection dataSource;

    public BookDAO(Connection dataSource) {
        this.dataSource = dataSource;
    }

    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        try {
            Statement stmt = dataSource.createStatement();
            String sql = "SELECT * FROM book";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) books.add(new Book(rs));

            rs.close();
            stmt.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return books;
    }

    public Optional<Book> getOneByBookNumber(String bookNumber) {
        try {
            Statement stmt = dataSource.createStatement();
            String sql = "SELECT * FROM book WHERE bno='" + bookNumber + "'";
            ResultSet rs = stmt.executeQuery(sql);

            Book book;
            if (rs.next()) book = new Book(rs);
            else return Optional.empty();

            rs.close();
            stmt.close();
            return Optional.of(book);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    public Book insert(Book book) {
        try {
            Statement stmt = dataSource.createStatement();

            String sql = "INSERT INTO book value (" +
                    book.getBookNumber() + "," + book.getCategory() + "," +
                    book.getTitle() + "," + book.getPress() + "," +
                    book.getYear() + "," + book.getAuthor() + "," +
                    book.getPrice() + "," + book.getTotal() + "," + book.getStock() + ")";

            ResultSet rs = stmt.executeQuery(sql);
            rs.close();
            stmt.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return book;
    }

    public Book update(Book book) {
        try {
            Statement stmt = dataSource.createStatement();

            String sql = "UPDATE book SET bno=" +
                    book.getBookNumber() + ", category=" + book.getCategory() + ", title=" +
                    book.getTitle() + ", press=" + book.getPress() + ", year=" +
                    book.getYear() + ", author=" + book.getAuthor() + ", price=" +
                    book.getPrice() + ", total=" + book.getTotal() + ", stock=" + book.getStock() + ")";

            ResultSet rs = stmt.executeQuery(sql);
            rs.close();
            stmt.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return book;
    }

    public void delete(String bookNumber) {
        try {
            Statement stmt = dataSource.createStatement();

            String sql = "DELETE FROM book WHERE bno=" + bookNumber;

            ResultSet rs = stmt.executeQuery(sql);
            rs.close();
            stmt.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
