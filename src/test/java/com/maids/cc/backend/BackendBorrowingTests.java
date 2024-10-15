package com.maids.cc.backend;


import com.maids.cc.backend.library.controllers.BorrowingRecordController;
import com.maids.cc.backend.library.entities.Book;
import com.maids.cc.backend.library.entities.Patron;
import com.maids.cc.backend.library.entities.BorrowingRecord;

import com.maids.cc.backend.library.services.BookService;
import com.maids.cc.backend.library.services.BorrowingRecordService;
import com.maids.cc.backend.library.services.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class BackendBorrowingTests {

    private MockMvc mockMvc;

    @Mock
    private BorrowingRecordService borrowingService;

    @Mock
    private BookService bookService;

    @Mock
    private PatronService patronService;

    @InjectMocks
    private BorrowingRecordController borrowingController;

    private Book testBook;
    private Patron testPatron;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(borrowingController).build();

        // Create a test book
        testBook = new Book("Test Book", "Test Author", 2021, "1234567890");
        testBook.setId(1L);
        when(bookService.create(any(Book.class))).thenReturn(testBook);
        when(bookService.findById(1L)).thenReturn(testBook);

        // Create a test patron
        testPatron = new Patron("John Doe", "john@example.com");
        testPatron.setId(1L);
        when(patronService.create(any(Patron.class))).thenReturn(testPatron);
        when(patronService.findById(1L)).thenReturn(testPatron);
    }

    @Test
    public void testBorrowBook() throws Exception {
        // Assume the book and patron are already in the database
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(testBook);
        borrowingRecord.setPatron(testPatron);
        borrowingRecord.setBorrowTime(LocalDateTime.now());

        when(borrowingService.borrowBook(1L, 1L)).thenReturn(borrowingRecord);

        mockMvc.perform(post("/borrow/{bookId}/patron/{patronId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.book.title").value("Test Book"))
                .andExpect(jsonPath("$.patron.name").value("John Doe"))
                .andExpect(jsonPath("$.borrowTime").isNotEmpty())
                .andExpect(jsonPath("$.returnTime").isEmpty());

        verify(borrowingService, times(1)).borrowBook(1L, 1L);
    }

    @Test
    public void testReturnBook() throws Exception {
        // Assume the book and patron are already in the database
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(testBook);
        borrowingRecord.setPatron(testPatron);
        borrowingRecord.setBorrowTime(LocalDateTime.now().minusDays(7));
        borrowingRecord.setReturnTime(LocalDateTime.now());

        when(borrowingService.returnBook(1L, 1L)).thenReturn(borrowingRecord);

        mockMvc.perform(put("/return/{bookId}/patron/{patronId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.book.title").value("Test Book"))
                .andExpect(jsonPath("$.patron.name").value("John Doe"))
                .andExpect(jsonPath("$.borrowTime").isNotEmpty())
                .andExpect(jsonPath("$.returnTime").isNotEmpty());

        verify(borrowingService, times(1)).returnBook(1L, 1L);
    }
    @Test
    void contextLoads() {
    }
}