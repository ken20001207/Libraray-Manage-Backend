package com.linyuanlin.LibraryManagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linyuanlin.LibraryManagement.model.Book;
import com.linyuanlin.LibraryManagement.model.Borrow;
import com.linyuanlin.LibraryManagement.model.Card;
import com.linyuanlin.LibraryManagement.model.CustomException;
import com.linyuanlin.LibraryManagement.service.BookService;
import com.linyuanlin.LibraryManagement.service.BorrowService;
import com.linyuanlin.LibraryManagement.service.CardService;
import io.javalin.http.Context;
import org.eclipse.jetty.http.HttpStatus;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.UUID.randomUUID;

public class BorrowController {

    final BorrowService borrowService;
    final BookService bookService;
    final CardService cardService;

    public BorrowController(Connection dataSource) {
        this.borrowService = new BorrowService(dataSource);
        this.bookService = new BookService(dataSource);
        this.cardService = new CardService(dataSource);
    }

    // 请求一笔借书记录
    public void getOneBorrowHandler(Context ctx) {

    }

    // 请求多笔借书记录
    public void getManyBorrowHandler(Context ctx) throws CustomException {

        Map<String, String> q = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : ctx.queryParamMap().entrySet()) {
            q.put(entry.getKey(), entry.getValue().get(0));
        }

        List<Borrow> borrows = borrowService.getMany(q);

        try {
            ObjectMapper mapper = new ObjectMapper();
            ctx.result(mapper.writeValueAsString(borrows));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CustomException(
                    "INTERNAL_SERVER_ERROR",
                    "failed to parse result into json",
                    HttpStatus.INTERNAL_SERVER_ERROR_500
            );
        }
    }

    // 借书
    public void insertBorrowHandler(Context ctx) throws CustomException {

        Optional<Book> findBookRes = bookService.getOne(ctx.formParam("bno"));

        if (findBookRes.isEmpty()) {
            throw new CustomException(
                    "BOOK_NOT_FOUND",
                    "Book with bookNumber " + ctx.formParam("bno") + " not found",
                    HttpStatus.NOT_FOUND_404
            );
        }

        Book book = findBookRes.get();

        if (book.getStock() == 0) {
            throw new CustomException(
                    "BOOK_OUT_OF_STOCK",
                    "Book with book number " + ctx.formParam("bno") + " is out of stock",
                    HttpStatus.NOT_ACCEPTABLE_406
            );
        }

        Optional<Card> findCardRes = cardService.getOne(ctx.formParam("cno"));

        if (findCardRes.isEmpty()) {
            throw new CustomException(
                    "CARD_NOT_FOUND",
                    "Card with card number " + ctx.formParam("cno") + " not found",
                    HttpStatus.NOT_FOUND_404
            );
        }

        Card card = findCardRes.get();

        Borrow borrow = new Borrow();
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
        borrow.setBorrowDate(sqlDate);
        borrow.setCardNumber(card.getCardNumber());
        borrow.setBookNumber(book.getBookNumber());
        borrow.setUuid(randomUUID().toString().substring(0, 18));

        book.setStock(book.getStock() - 1);
        bookService.update(book);

        borrow = borrowService.insert(borrow);

        try {
            ObjectMapper mapper = new ObjectMapper();
            ctx.result(mapper.writeValueAsString(borrow));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CustomException(
                    "INTERNAL_SERVER_ERROR",
                    "failed to parse result into json",
                    HttpStatus.INTERNAL_SERVER_ERROR_500
            );
        }
    }

    // 还书
    public void updateBorrowHandler(Context ctx) throws CustomException {

        Optional<Borrow> b = borrowService.getOne(ctx.pathParam("uuid"));

        if (b.isEmpty()) {
            throw new CustomException(
                    "BORROW_NOT_FOUND",
                    "Borrow with uuid " + ctx.pathParam("uuid") + " not found",
                    HttpStatus.NOT_FOUND_404
            );
        }

        Optional<Book> book = bookService.getOne(b.get().getBookNumber());

        if (book.isEmpty()) {
            throw new CustomException(
                    "BOOK_NOT_FOUND",
                    "Book with bookNumber " + b.get().getBookNumber() + " not found",
                    HttpStatus.NOT_FOUND_404
            );
        }
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
        b.get().setReturnDate(sqlDate);
        book.get().setStock(book.get().getStock() + 1);

        borrowService.update(b.get());
        bookService.update(book.get());

        try {
            ObjectMapper mapper = new ObjectMapper();
            ctx.result(mapper.writeValueAsString(b.get()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CustomException(
                    "INTERNAL_SERVER_ERROR",
                    "failed to parse result into json",
                    HttpStatus.INTERNAL_SERVER_ERROR_500
            );
        }
    }
}
