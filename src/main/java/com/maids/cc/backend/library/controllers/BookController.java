package com.maids.cc.backend.library.controllers;

import com.maids.cc.backend.library.entities.Book;
import com.maids.cc.backend.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("BookController")
@RequestMapping("/books")
public class BookController extends BaseController<Book, BookService> {

    @Autowired
    @Qualifier("BookService")  // or the name of the specific BookService bean
    private BookService bookService;

}
