package com.linyuanlin.LibraryManagement.service;

import com.linyuanlin.LibraryManagement.model.Book;
import com.linyuanlin.LibraryManagement.model.CustomException;
import com.linyuanlin.LibraryManagement.repository.BookDAO;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class BookService {

    final BookDAO bookDAO;

    public BookService(Connection dataSource) {
        this.bookDAO = new BookDAO(dataSource);
    }

    public Optional<Book> getOne(String bookNumber) {
        return bookDAO.getOneByBookNumber(bookNumber);
    }

    public List<Book> getMany() {
        return bookDAO.getAll();
    }

    public Book insert(Book book) throws CustomException {
        return bookDAO.insert(book);
    }

    public void delete(String bookNumber) {
        bookDAO.delete(bookNumber);
    }

    public Book update(Book book) {
        return bookDAO.update(book);
    }
}
