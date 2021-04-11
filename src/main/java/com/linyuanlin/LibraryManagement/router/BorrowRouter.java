package com.linyuanlin.LibraryManagement.router;

import com.linyuanlin.LibraryManagement.controller.BorrowController;
import io.javalin.Javalin;

import java.sql.Connection;

public class BorrowRouter {
    public static void setUp(Javalin app, Connection conn) {

        BorrowController borrowController = new BorrowController(conn);

        app.get("/borrow/:uuid", borrowController::getOneBorrowHandler);

        app.get("/borrow", borrowController::getManyBorrowHandler);

        app.post("/borrow/:uuid", borrowController::updateBorrowHandler);

        app.post("/borrow", borrowController::insertBorrowHandler);

        app.delete("/borrow/:uuid", borrowController::deleteBorrowHandler);
    }
}
