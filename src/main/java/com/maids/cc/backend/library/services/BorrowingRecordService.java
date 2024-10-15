package com.maids.cc.backend.library.services;

import com.maids.cc.backend.library.entities.BorrowingRecord;
import com.maids.cc.backend.library.repositories.BorrowingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


import java.time.LocalDateTime;

@Service("BorrowingRecordService")
public class BorrowingRecordService extends BaseServices<BorrowingRecord> {
    @Autowired
    BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    BookService bookService;

    @Autowired
    PatronService patronService;

    public BorrowingRecord borrowBook(Long bookId, Long patronId) {

        if (isBorrowed(bookId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book is borrowed");

        try {
            BorrowingRecord br = new BorrowingRecord(
                bookService.findById(bookId),
                patronService.findById(patronId),
                    LocalDateTime.now(),
                    null
            );
            return this.create(br);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book or patron not found!");
        }
    }

    public boolean isBorrowed(Long bookId) {
        return this.borrowingRecordRepository.checkBookIfBorrowed(bookId).isPresent();
    }

    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        try {

            BorrowingRecord borrowingRecord = this.borrowingRecordRepository.getPatronBorrowedBook(bookId, patronId).orElse(null);
            if (borrowingRecord == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
            borrowingRecord.setReturnTime(
                    LocalDateTime.now()
            );

            return this.baseRepository.save(borrowingRecord);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
        }
    }
}
