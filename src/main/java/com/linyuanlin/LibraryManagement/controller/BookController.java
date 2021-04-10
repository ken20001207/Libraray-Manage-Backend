package com.linyuanlin.LibraryManagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linyuanlin.LibraryManagement.model.Book;
import com.linyuanlin.LibraryManagement.service.BookService;
import io.javalin.http.Context;

import java.sql.Connection;
import java.util.Optional;

public class BookController {

    final BookService bookService;

    public BookController(Connection dataSource) {
        this.bookService = new BookService(dataSource);
    }

    public void getOneBookHandler(Context ctx) {

        Optional<Book> book = bookService.getOneByBookNumber(ctx.pathParam("bookNumber"));

        if (book.isEmpty()) {
            ctx.status(404);
            ctx.result("{\"code\": \"BOOK_NOT_FOUND\", \"book_number\": " + ctx.pathParam("bookNumber") + "}");
            return;
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
}
