package com.linyuanlin.LibraryManagement;

import com.linyuanlin.LibraryManagement.controller.BookController;
import io.javalin.Javalin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        Javalin app = Javalin.create(config ->
                config.requestLogger((ctx, ms) ->
                        System.out.println(ctx.status() + " " + ctx.method() + " " + ctx.url() + " " + ms + " ms")
                )
        ).start(7000);

        Connection conn;
        try {
            String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
            String MYSQL_URL = System.getenv("MYSQL_URL");
            String MYSQL_USER = System.getenv("MYSQL_USER");
            String MYSQL_PASS = System.getenv("MYSQL_PASS");
            Class.forName(JDBC_DRIVER);
            System.out.println("正在连接至数据库 ...");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println();
            System.out.println("\u001B[31m" + "数据库连线失败，请检查环境变数是否有正确配置 MySQL 连线资料。");
            System.out.println("\u001B[31m" + "查看 readme.md 或其他文件来取得设定说明。");
            return;
        }
        System.out.println("数据库链接成功！");

        BookController bookController = new BookController(conn);
        app.get("/book/:bookNumber", bookController::getOneBookHandler);

        System.out.println("服务初始化完毕，可以透过 7000 端口访问。");
    }
}
