package com.linyuanlin.LibraryManagement.repository;


import com.linyuanlin.LibraryManagement.model.Book;
import com.linyuanlin.LibraryManagement.model.CustomException;
import org.eclipse.jetty.http.HttpStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BookDAO {
    final Connection dataSource;

    public BookDAO(Connection dataSource) {
        this.dataSource = dataSource;
    }

    public List<Book> getAll(Map<String, String> query) {
        List<Book> books = new ArrayList<>();
        try {
            Statement stmt = dataSource.createStatement();

            StringBuilder sql = new StringBuilder("SELECT * FROM book WHERE ");
            sql.append("year <= ").append(query.get("year_top"));
            sql.append(" AND year >= ").append(query.get("year_bottom"));
            sql.append(" AND price <= ").append(query.get("price_top"));
            sql.append(" AND price >= ").append(query.get("price_bottom"));

            if (!query.get("book_number").equals(""))
                sql.append(" AND bno = '").append(query.get("book_number")).append("'");

            if (!query.get("title").equals(""))
                sql.append(" AND title = '").append(query.get("title")).append("'");

            if (!query.get("author").equals(""))
                sql.append(" AND author = '").append(query.get("author")).append("'");

            if (!query.get("press").equals(""))
                sql.append(" AND press = '").append(query.get("press")).append("'");

            if (!query.get("category").equals(""))
                sql.append(" AND category = '").append(query.get("category")).append("'");

            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql.toString());

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

    public Book insert(Book book) throws CustomException {
        try {
            Statement stmt = dataSource.createStatement();

            String sql = "INSERT INTO book value ('" +
                    book.getBookNumber() + "','" + book.getCategory() + "','" +
                    book.getTitle() + "','" + book.getPress() + "','" +
                    book.getYear() + "','" + book.getAuthor() + "'," +
                    book.getPrice() + "," + book.getTotal() + "," + book.getStock() + ")";

            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException throwable) {
            throw new CustomException(
                    "INTERNAL_SERVER_ERROR",
                    "SQL Error: " + throwable.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR_500
            );
        }
        return book;
    }

    public Book update(Book book) {
        try {
            Statement stmt = dataSource.createStatement();

            String sql = "UPDATE book SET category='" + book.getCategory() + "', title='" +
                    book.getTitle() + "', press='" + book.getPress() + "', year=" +
                    book.getYear() + ", author='" + book.getAuthor() + "', price=" +
                    book.getPrice() + ", total=" + book.getTotal() + ", stock=" + book.getStock() + " WHERE bno='" + book.getBookNumber() + "'";
            System.out.println(sql);
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return book;
    }

    public void delete(String bookNumber) {
        try {
            Statement stmt = dataSource.createStatement();
            String sql = "DELETE FROM book WHERE bno='" + bookNumber + "'";
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
