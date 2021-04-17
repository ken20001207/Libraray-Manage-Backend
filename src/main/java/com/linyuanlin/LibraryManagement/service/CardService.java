package com.linyuanlin.LibraryManagement.service;

import com.linyuanlin.LibraryManagement.model.Card;
import com.linyuanlin.LibraryManagement.model.CustomException;
import com.linyuanlin.LibraryManagement.repository.CardDAO;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class CardService {

    final CardDAO cardDAO;

    public CardService(Connection dataSource) {
        this.cardDAO = new CardDAO(dataSource);
    }

    public Optional<Card> getOne(String cardNumber) {
        return cardDAO.getOneByCardNumber(cardNumber);
    }

    public List<Card> getMany() {
        return cardDAO.getAll();
    }

    public Card insert(Card newCard) throws CustomException {
        return cardDAO.insert(newCard);
    }

}
