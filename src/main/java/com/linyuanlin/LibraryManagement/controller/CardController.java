package com.linyuanlin.LibraryManagement.controller;

import com.linyuanlin.LibraryManagement.service.CardService;
import io.javalin.http.Context;

import java.sql.Connection;

public class CardController {

    final CardService cardService;

    public CardController(Connection dataSource) {
        this.cardService = new CardService(dataSource);
    }

    public void getOneCardHandler(Context ctx) {

    }

    public void getManyCardHandler(Context ctx) {

    }

    public void insertCardHandler(Context ctx) {

    }

    public void updateCardHandler(Context ctx) {

    }

    public void deleteCardHandler(Context ctx) {

    }
}
