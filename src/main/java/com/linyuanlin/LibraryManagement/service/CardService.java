package com.linyuanlin.LibraryManagement.service;

import com.linyuanlin.LibraryManagement.model.Card;
import com.linyuanlin.LibraryManagement.repository.CardDAO;

import java.sql.Connection;
import java.util.Optional;

public class CardService {

    final CardDAO cardDAO;

    public CardService(Connection dataSource) {
        this.cardDAO = new CardDAO(dataSource);
    }

    public Optional<Card> getOne(String cardNumber) {
        return cardDAO.getOneByCardNumber(cardNumber);
    }

    public Card insert(Card newCard) {
        return cardDAO.insert(newCard);
    }

}
