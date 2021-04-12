package com.linyuanlin.LibraryManagement.service;

import com.linyuanlin.LibraryManagement.model.Borrow;
import com.linyuanlin.LibraryManagement.repository.BorrowDAO;

import java.sql.Connection;

public class BorrowService {

    final BorrowDAO borrowDAO;

    public BorrowService(Connection dataSource) {
        this.borrowDAO = new BorrowDAO(dataSource);
    }

    public Borrow insert(Borrow newBorrow) {
        return borrowDAO.insert(newBorrow);
    }

}
