package com.linyuanlin.LibraryManagement.service;

import com.linyuanlin.LibraryManagement.repository.BorrowDAO;

import java.sql.Connection;

public class BorrowService {

    final BorrowDAO borrowDAO;

    public BorrowService(Connection dataSource) {
        this.borrowDAO = new BorrowDAO(dataSource);
    }

}
