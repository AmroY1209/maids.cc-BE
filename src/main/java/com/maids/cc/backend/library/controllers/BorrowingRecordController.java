package com.maids.cc.backend.library.controllers;

import com.maids.cc.backend.library.entities.BorrowingRecord;
import com.maids.cc.backend.library.services.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class BorrowingRecordController {
    @Autowired
    BorrowingRecordService borrowingRecordService;

    @PostMapping("borrow/{book_id}/patron/{patron_id}")
    public BorrowingRecord borrowBook(
            @PathVariable("book_id") Long bookId,
            @PathVariable("patron_id") Long patronId
    ) {
        return borrowingRecordService.borrowBook(bookId, patronId);
    }

    @PutMapping("return/{book_id}/patron/{patron_id}")
    public BorrowingRecord returnBook(
            @PathVariable("book_id") Long bookId,
            @PathVariable("patron_id") Long patronId
    ) {
        return borrowingRecordService.returnBook(bookId, patronId);
    }

}
