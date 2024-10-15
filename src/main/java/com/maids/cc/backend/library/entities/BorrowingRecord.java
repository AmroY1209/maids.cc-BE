package com.maids.cc.backend.library.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "borrowing_record")
@Entity(name = "borrowing_record")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingRecord extends Base {

    @OneToOne(targetEntity = Book.class, fetch = FetchType.EAGER)
    private Book book;

    @OneToOne(targetEntity = Patron.class, fetch = FetchType.EAGER)
    private Patron patron;

    LocalDateTime borrowTime;
    LocalDateTime returnTime;



}
