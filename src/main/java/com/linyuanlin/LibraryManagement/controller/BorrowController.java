package com.linyuanlin.LibraryManagement.controller;

import com.linyuanlin.LibraryManagement.service.BorrowService;
import io.javalin.http.Context;

import java.sql.Connection;

public class BorrowController {

    final BorrowService borrowService;

    public BorrowController(Connection dataSource) {
        this.borrowService = new BorrowService(dataSource);
    }

    public void getOneBorrowHandler(Context ctx) {

    }

    public void getManyBorrowHandler(Context ctx) {

    }

    public void insertBorrowHandler(Context ctx) {

    }

    public void updateBorrowHandler(Context ctx) {

    }

    public void deleteBorrowHandler(Context ctx) {

    }
}
