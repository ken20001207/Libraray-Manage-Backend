package com.linyuanlin.LibraryManagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linyuanlin.LibraryManagement.model.Book;
import com.linyuanlin.LibraryManagement.model.CustomException;
import com.linyuanlin.LibraryManagement.service.BookService;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;

import java.sql.Connection;
import java.util.Optional;

public class BookController {

    final BookService bookService;

    public BookController(Connection dataSource) {
        this.bookService = new BookService(dataSource);
    }

    public void getOneBookHandler(Context ctx) throws CustomException {

        Optional<Book> book = bookService.getOne(ctx.pathParam("bookNumber"));

        if (book.isEmpty()) {
            throw new CustomException(
                    "BOOK_NOT_FOUND",
                    "Book with bookNumber " + ctx.pathParam("bookNumber") + " not found",
                    HttpStatus.NOT_FOUND_404
            );
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            ctx.result(mapper.writeValueAsString(book.get()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("{\"code\": \"INTERNAL_SERVER_ERROR\"}");
        }
    }

    public void getManyBookHandler(Context ctx) {

    }

    public void insertBookHandler(Context ctx) {

    }

    public void updateBookHandler(Context ctx) {

    }

    public void deleteBookHandler(Context ctx) {

    }
}
