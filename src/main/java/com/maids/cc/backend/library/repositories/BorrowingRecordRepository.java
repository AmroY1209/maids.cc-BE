package com.maids.cc.backend.library.repositories;

import com.maids.cc.backend.library.entities.BorrowingRecord;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BorrowingRecordRepository extends BaseRepository<BorrowingRecord> {

    @Query(
            value = """
                    SELECT *
                    FROM borrowing_record
                    WHERE book_id = :book
                    AND return_time IS NULL
                    limit 1
                    """
            ,nativeQuery = true
    )
    public Optional<BorrowingRecord> checkBookIfBorrowed(Long book);

    @Query(
            value = """
                    SELECT *
                    FROM borrowing_record
                    WHERE book_id = :book
                    AND patron_id = :patron
                    AND return_time IS NULL
                    limit 1
                    """
            ,nativeQuery = true
    )
    public Optional<BorrowingRecord> getPatronBorrowedBook(Long book, Long patron);

}
