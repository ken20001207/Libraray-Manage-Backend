package com.linyuanlin.LibraryManagement.router;

import com.linyuanlin.LibraryManagement.controller.BookController;
import io.javalin.Javalin;

import java.sql.Connection;

public class BookRouter {
    public static void setUp(Javalin app, Connection conn) {

        BookController bookController = new BookController(conn);

        app.get("/book/:bookNumber", bookController::getOneBookHandler);

        app.get("/book", bookController::getManyBookHandler);

        app.post("/book/:bookNumber", bookController::updateBookHandler);

        app.post("/book", bookController::insertBookHandler);

        app.delete("/book/:bookNumber", bookController::deleteBookHandler);
    }
}
