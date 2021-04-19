package com.linyuanlin.LibraryManagement.repository;


import com.linyuanlin.LibraryManagement.model.Borrow;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BorrowDAO {
    final Connection dataSource;

    public BorrowDAO(Connection dataSource) {
        this.dataSource = dataSource;
    }

    public List<Borrow> getAll(Map<String, String> query) {
        List<Borrow> borrows = new ArrayList<>();
        try {
            Statement stmt = dataSource.createStatement();

            StringBuilder sql = new StringBuilder("SELECT * FROM borrow WHERE true");

            if (!query.get("card_number").equals(""))
                sql.append(" AND cno = '").append(query.get("card_number")).append("'");

            if (!query.get("book_number").equals(""))
                sql.append(" AND bno = '").append(query.get("book_number")).append("'");

            if (!query.get("uuid").equals(""))
                sql.append(" AND uuid = '").append(query.get("uuid")).append("'");

            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql.toString());

            while (rs.next()) borrows.add(new Borrow(rs));

            rs.close();
            stmt.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return borrows;
    }

    public Optional<Borrow> getOneByUUID(String uuid) {
        try {
            Statement stmt = dataSource.createStatement();
            String sql = "SELECT * FROM borrow WHERE uuid='" + uuid + "'";
            ResultSet rs = stmt.executeQuery(sql);

            Borrow borrow;
            if (rs.next()) borrow = new Borrow(rs);
            else return Optional.empty();

            rs.close();
            stmt.close();
            return Optional.of(borrow);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    public Borrow insert(Borrow borrow) {
        try {
            Statement stmt = dataSource.createStatement();

            String sql = "INSERT INTO borrow value ('"
                    + borrow.getUuid() + "', '" + borrow.getCardNumber() + "', '"
                    + borrow.getBookNumber() + "', '" + borrow.getBorrowDate() + "', null)";

            stmt.execute(sql);
            stmt.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return borrow;
    }

    public Borrow update(Borrow borrow) {
        try {
            Statement stmt = dataSource.createStatement();

            String sql = "UPDATE borrow SET cno='" + borrow.getCardNumber() + "', bno='"
                    + borrow.getBookNumber() + "', borrow_date='" + borrow.getBorrowDate() + "', return_date='"
                    + borrow.getReturnDate() + "' WHERE uuid='" + borrow.getUuid() + "'";
            System.out.println(sql);
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return borrow;
    }

    public void delete(String uuid) {
        try {
            Statement stmt = dataSource.createStatement();

            String sql = "DELETE FROM borrow WHERE uuid=" + uuid;

            stmt.execute(sql);
            stmt.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
