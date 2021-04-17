package com.linyuanlin.LibraryManagement.controller;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linyuanlin.LibraryManagement.model.Card;
import com.linyuanlin.LibraryManagement.model.CustomException;
import com.linyuanlin.LibraryManagement.service.CardService;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;

import java.sql.Connection;
import java.util.List;

public class CardController {

    final CardService cardService;

    public CardController(Connection dataSource) {
        this.cardService = new CardService(dataSource);
    }

    // 查询一笔借书证信息
    public void getOneCardHandler(Context ctx) {

    }

    // 查询多笔借书证信息
    public void getManyCardHandler(Context ctx) throws CustomException {
        List<Card> cards = cardService.getMany();

        try {
            ObjectMapper mapper = new ObjectMapper();
            ctx.result(mapper.writeValueAsString(cards));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CustomException(
                    "INTERNAL_SERVER_ERROR",
                    "failed to parse result into json",
                    HttpStatus.INTERNAL_SERVER_ERROR_500
            );
        }
    }

    // 申办新的借书证
    public void insertCardHandler(Context ctx) throws CustomException {

        Card newCard = new Card();

        newCard.setCardNumber(NanoIdUtils.randomNanoId(
                NanoIdUtils.DEFAULT_NUMBER_GENERATOR,
                "1234567890".toCharArray(),
                7)
        );
        newCard.setDepartment(ctx.formParam("department"));
        newCard.setName(ctx.formParam("name"));
        newCard.setType(ctx.formParam("type").charAt(0));

        newCard = cardService.insert(newCard);

        try {
            ObjectMapper mapper = new ObjectMapper();
            ctx.result(mapper.writeValueAsString(newCard));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CustomException(
                    "INTERNAL_SERVER_ERROR",
                    "failed to parse result into json",
                    HttpStatus.INTERNAL_SERVER_ERROR_500
            );
        }
    }

    // 更新借书证信息
    public void updateCardHandler(Context ctx) {

    }

    // 借书证注销
    public void deleteCardHandler(Context ctx) {

    }
}
