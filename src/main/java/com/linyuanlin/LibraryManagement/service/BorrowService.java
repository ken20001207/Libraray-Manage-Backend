package com.linyuanlin.LibraryManagement.service;

import com.linyuanlin.LibraryManagement.model.Borrow;
import com.linyuanlin.LibraryManagement.repository.BorrowDAO;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class BorrowService {

    final BorrowDAO borrowDAO;

    public BorrowService(Connection dataSource) {
        this.borrowDAO = new BorrowDAO(dataSource);
    }

    public Borrow insert(Borrow newBorrow) {
        return borrowDAO.insert(newBorrow);
    }

    public Optional<Borrow> getOne(String uuid) {
        return borrowDAO.getOneByUUID(uuid);
    }

    public List<Borrow> getMany() {
        return borrowDAO.getAll();
    }

    public Borrow update(Borrow borrow) {
        return borrowDAO.update(borrow);
    }

}
