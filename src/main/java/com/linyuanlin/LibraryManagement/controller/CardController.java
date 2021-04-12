package com.linyuanlin.LibraryManagement.controller;

import com.linyuanlin.LibraryManagement.service.CardService;
import io.javalin.http.Context;

import java.sql.Connection;

public class CardController {

    final CardService cardService;

    public CardController(Connection dataSource) {
        this.cardService = new CardService(dataSource);
    }

    // 查询一笔借书证信息
    public void getOneCardHandler(Context ctx) {

    }

    // 查询多笔借书证信息
    public void getManyCardHandler(Context ctx) {

    }

    // 申办新的借书证
    public void insertCardHandler(Context ctx) {

    }

    // 更新借书证信息
    public void updateCardHandler(Context ctx) {

    }

    // 借书证注销
    public void deleteCardHandler(Context ctx) {

    }
}
