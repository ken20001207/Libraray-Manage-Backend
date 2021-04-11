package com.linyuanlin.LibraryManagement.service;

import com.linyuanlin.LibraryManagement.repository.CardDAO;

import java.sql.Connection;

public class CardService {

    final CardDAO cardDAO;

    public CardService(Connection dataSource) {
        this.cardDAO = new CardDAO(dataSource);
    }

}
