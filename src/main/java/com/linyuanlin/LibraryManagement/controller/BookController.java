package com.linyuanlin.LibraryManagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linyuanlin.LibraryManagement.model.Book;
import com.linyuanlin.LibraryManagement.model.CustomException;
import com.linyuanlin.LibraryManagement.service.BookService;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.http.HttpStatus;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BookController {

    final BookService bookService;

    public BookController(Connection dataSource) {
        this.bookService = new BookService(dataSource);
    }

    // 批量导入书籍
    public void importBooksFromFile(Context ctx) throws IOException, CustomException {
        List<UploadedFile> files = ctx.uploadedFiles();
        StringWriter writer = new StringWriter();
        IOUtils.copy(files.get(0).getContent(), writer, StandardCharsets.UTF_8);
        String data = writer.toString().replace("(", "").replace(")", "");
        String[] lines = data.split("\n");
        for (String line : lines) {
            String[] cols = line.split(",");
            Book newBook = new Book();
            newBook.setBookNumber(cols[0]);
            newBook.setCategory(cols[1]);
            newBook.setTitle(cols[2]);
            newBook.setPress(cols[3]);
            newBook.setYear(Integer.parseInt(cols[4]));
            newBook.setAuthor(cols[5]);
            newBook.setPrice(Double.parseDouble(cols[6]));
            newBook.setTotal(Integer.parseInt(cols[7]));
            newBook.setStock(Integer.parseInt(cols[7]));
            bookService.insert(newBook);
        }
        ctx.result(String.valueOf(lines.length));
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

        Map<String, String> q = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : ctx.queryParamMap().entrySet()) {
            q.put(entry.getKey(), entry.getValue().get(0));
        }
        List<Book> books = bookService.getMany(q);

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
    public void insertBookHandler(Context ctx) throws CustomException {

        Book newBook = new Book();

        newBook.setTitle(ctx.formParam("title"));
        newBook.setBookNumber(ctx.formParam("bno"));
        newBook.setAuthor(ctx.formParam("author"));
        newBook.setPress(ctx.formParam("press"));
        newBook.setCategory(ctx.formParam("category"));

        newBook.setYear(Integer.parseInt(ctx.formParam("year")));
        newBook.setTotal(Integer.parseInt(ctx.formParam("total")));
        newBook.setStock(Integer.parseInt(ctx.formParam("total")));

        newBook = bookService.insert(newBook);

        try {
            ObjectMapper mapper = new ObjectMapper();
            ctx.result(mapper.writeValueAsString(newBook));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CustomException(
                    "INTERNAL_SERVER_ERROR",
                    "failed to parse result into json",
                    HttpStatus.INTERNAL_SERVER_ERROR_500
            );
        }
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
        newBook.setAuthor(ctx.formParam("author"));
        newBook.setCategory(ctx.formParam("category"));
        newBook.setPress(ctx.formParam("press"));

        newBook.setTotal(Integer.parseInt(ctx.formParam("total")));
        newBook.setPrice(Integer.parseInt(ctx.formParam("price")));
        newBook.setStock(Integer.parseInt(ctx.formParam("stock")));

        newBook = bookService.update(newBook);

        try {
            ObjectMapper mapper = new ObjectMapper();
            ctx.result(mapper.writeValueAsString(newBook));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CustomException(
                    "INTERNAL_SERVER_ERROR",
                    "failed to parse result into json",
                    HttpStatus.INTERNAL_SERVER_ERROR_500
            );
        }
    }

    // 移除书本
    public void deleteBookHandler(Context ctx) {
        bookService.delete(ctx.pathParam("bookNumber"));
    }
}
