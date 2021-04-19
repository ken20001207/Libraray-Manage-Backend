package com.linyuanlin.LibraryManagement.repository;


import com.linyuanlin.LibraryManagement.model.Card;
import com.linyuanlin.LibraryManagement.model.CustomException;
import org.eclipse.jetty.http.HttpStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CardDAO {
    final Connection dataSource;

    public CardDAO(Connection dataSource) {
        this.dataSource = dataSource;
    }

    public List<Card> getAll(Map<String, String> query) {
        List<Card> cards = new ArrayList<>();
        try {
            Statement stmt = dataSource.createStatement();
            StringBuilder sql = new StringBuilder("SELECT * FROM card WHERE true");

            if (!query.get("name").equals(""))
                sql.append(" AND name = '").append(query.get("name")).append("'");

            if (!query.get("card_number").equals(""))
                sql.append(" AND cno = '").append(query.get("card_number")).append("'");

            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql.toString());

            while (rs.next()) cards.add(new Card(rs));

            rs.close();
            stmt.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return cards;
    }

    public Optional<Card> getOneByCardNumber(String cardNumber) {
        try {
            Statement stmt = dataSource.createStatement();
            String sql = "SELECT * FROM card WHERE cno='" + cardNumber + "'";
            ResultSet rs = stmt.executeQuery(sql);

            Card card;
            if (rs.next()) card = new Card(rs);
            else return Optional.empty();

            rs.close();
            stmt.close();
            return Optional.of(card);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    public Card insert(Card card) throws CustomException {
        try {
            Statement stmt = dataSource.createStatement();

            String sql = "INSERT INTO card value ('"
                    + card.getCardNumber() + "','" + card.getName() + "','" + card.getDepartment() + "','"
                    + card.getType() + "')";

            stmt.execute(sql);
            stmt.close();
        } catch (SQLException throwable) {
            throw new CustomException(
                    "INTERNAL_SERVER_ERROR",
                    "SQL Error: " + throwable.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR_500
            );
        }
        return card;
    }

    public Card update(Card card) {
        try {
            Statement stmt = dataSource.createStatement();

            String sql = "UPDATE card SET cno='"
                    + card.getCardNumber() + "', name='" + card.getName() + "', department='"
                    + card.getDepartment() + "', type=" + card.getType() + ")";

            stmt.execute(sql);
            stmt.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return card;
    }

    public void delete(String cardNumber) {
        try {
            Statement stmt = dataSource.createStatement();

            String sql = "DELETE FROM card WHERE cno='" + cardNumber + "'";

            stmt.execute(sql);
            stmt.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
