package com.linyuanlin.LibraryManagement.controller;

import com.linyuanlin.LibraryManagement.service.BorrowService;
import io.javalin.http.Context;

import java.sql.Connection;

public class BorrowController {

    final BorrowService borrowService;

    public BorrowController(Connection dataSource) {
        this.borrowService = new BorrowService(dataSource);
    }

    // 请求一笔借书记录
    public void getOneBorrowHandler(Context ctx) {

    }

    // 请求多笔借书记录
    public void getManyBorrowHandler(Context ctx) {

    }

    // 借书
    public void insertBorrowHandler(Context ctx) {

    }

    // 还书
    public void updateBorrowHandler(Context ctx) {

    }
}
