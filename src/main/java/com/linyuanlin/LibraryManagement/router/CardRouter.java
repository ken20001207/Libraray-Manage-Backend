package com.linyuanlin.LibraryManagement.router;

import com.linyuanlin.LibraryManagement.controller.CardController;
import io.javalin.Javalin;

import java.sql.Connection;

public class CardRouter {
    public static void setUp(Javalin app, Connection conn) {

        CardController cardController = new CardController(conn);

        app.get("/card/:cardNumber", cardController::getOneCardHandler);

        app.get("/card", cardController::getManyCardHandler);

        app.post("/card/:cardNumber", cardController::updateCardHandler);

        app.post("/card", cardController::insertCardHandler);

        app.delete("/card/:cardNumber", cardController::deleteCardHandler);
    }
}
