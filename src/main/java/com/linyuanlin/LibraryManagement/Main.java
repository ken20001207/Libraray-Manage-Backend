package com.linyuanlin.LibraryManagement;

import com.linyuanlin.LibraryManagement.model.CustomException;
import com.linyuanlin.LibraryManagement.router.BookRouter;
import com.linyuanlin.LibraryManagement.router.BorrowRouter;
import com.linyuanlin.LibraryManagement.router.CardRouter;
import io.javalin.Javalin;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        // Start Http Service 启动 Http 服务

        Javalin app = Javalin.create(config ->
                config.requestLogger((ctx, ms) ->
                        System.out.println(ctx.status() + " " + ctx.method() + " " + ctx.url() + " " + ms + " ms")
                )
        ).start(7000);

        // Declare all the response is JSON type 声明服务的所有回传都是 JSON 格式

        app.after(ctx -> {
            ctx.header("Content-Type", "application/json");
        });

        // Register Exception Handler 注册错误处理中间件

        app.exception(CustomException.class, (e, ctx) -> {
            ctx.status(e.getStatus());
            ctx.result(e.getJson());
        });

        // Connect to MySQL Database 连线至数据库

        Connection conn = null;
        try {
            conn = DataSource.setupConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Setup API Routes 注册 API 路由

        BookRouter.setUp(app, conn);
        CardRouter.setUp(app, conn);
        BorrowRouter.setUp(app, conn);

        // Print initialized message 输入初始化完毕讯息

        System.out.println("服务初始化完毕，可以透过 7000 端口访问。");
    }
}
