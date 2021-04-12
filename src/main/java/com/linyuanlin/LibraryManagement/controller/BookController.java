package com.linyuanlin.LibraryManagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linyuanlin.LibraryManagement.model.Book;
import com.linyuanlin.LibraryManagement.model.CustomException;
import com.linyuanlin.LibraryManagement.service.BookService;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class BookController {

    final BookService bookService;

    public BookController(Connection dataSource) {
        this.bookService = new BookService(dataSource);
    }

    // 请求一本书
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
            throw new CustomException(
                    "INTERNAL_SERVER_ERROR",
                    "failed to parse result into json",
                    HttpStatus.INTERNAL_SERVER_ERROR_500
            );
        }
    }

    // 请求多本书
    public void getManyBookHandler(Context ctx) throws CustomException {
        List<Book> books = bookService.getMany();
        try {
            ObjectMapper mapper = new ObjectMapper();
            ctx.result(mapper.writeValueAsString(books));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CustomException(
                    "INTERNAL_SERVER_ERROR",
                    "failed to parse result into json",
                    HttpStatus.INTERNAL_SERVER_ERROR_500
            );
        }
    }

    // 新书入库
    public void insertBookHandler(Context ctx) {
        Book newBook = new Book();
        newBook.setTitle(ctx.formParam("title"));
        newBook.setBoolNumber(ctx.formParam("bno"));
        newBook.setAuthor(ctx.formParam("author"));
        newBook.setCategory(ctx.formParam("category"));
        newBook.setTotal(Integer.parseInt(ctx.formParam("total")));
        newBook.setStock(Integer.parseInt(ctx.formParam("total")));
        bookService.insert(newBook);
    }

    // 更新书本信息
    public void updateBookHandler(Context ctx) throws CustomException {
        Optional<Book> res = bookService.getOne(ctx.pathParam("bookNumber"));

        if (res.isEmpty()) {
            throw new CustomException(
                    "BOOK_NOT_FOUND",
                    "Book with bookNumber " + ctx.pathParam("bookNumber") + " not found",
                    HttpStatus.NOT_FOUND_404
            );
        }

        Book newBook = res.get();
        newBook.setTitle(ctx.formParam("title"));
        newBook.setBoolNumber(ctx.formParam("bno"));
        newBook.setAuthor(ctx.formParam("author"));
        newBook.setCategory(ctx.formParam("category"));
        newBook.setTotal(Integer.parseInt(ctx.formParam("total")));
        newBook.setStock(Integer.parseInt(ctx.formParam("total")));

        bookService.update(newBook);
    }

    // 移除书本
    public void deleteBookHandler(Context ctx) {
        bookService.delete(ctx.pathParam("bookNumber"));
    }
}
