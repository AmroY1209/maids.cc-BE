package com.maids.cc.backend;

import com.maids.cc.backend.library.controllers.BookController;
import com.maids.cc.backend.library.entities.Book;
import com.maids.cc.backend.library.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class BackendBooksTests {

	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	@InjectMocks
	private BookController bookController;

	private ObjectMapper objectMapper = new ObjectMapper();


	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
	}

	@Test
	public void testFindAll() throws Exception {

		List<Book> books = Arrays.asList(
				new Book(
						"Book 1",
						"Auth1",
						2010,
						"1234567890"
				),
				new Book("Book 2", "Auth2", 2014, "0987654321")
		);

		when(bookService.findAll()).thenReturn(books);

		mockMvc.perform(get("/books"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Book 1"))
				.andExpect(jsonPath("$[1].title").value("Book 2"));

		verify(bookService, times(1)).findAll();
	}

	@Test
	public void testFindById() throws Exception {
		Book book = new Book("Book 1", "Author 1", 2010, "1234567890");

		when(bookService.findById(1L)).thenReturn(book);

		mockMvc.perform(get("/books/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("Book 1"));

		verify(bookService, times(1)).findById(1L);
	}

	@Test
	public void testCreateBook() throws Exception {
		Book book = new Book("New Book", "New Author", 2011, "1234567890");

		when(bookService.create(any(Book.class))).thenReturn(book);

		mockMvc.perform(post("/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(book)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("New Book"));

		verify(bookService, times(1)).create(any(Book.class));
	}

	@Test
	public void testUpdateBook() throws Exception {
		Book updatedBook = new Book("Updated Book", "Updated Author", 2032, "1234567890");

		when(bookService.update(eq(1L), any(Book.class))).thenReturn(updatedBook);

		mockMvc.perform(put("/books/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedBook)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("Updated Book"));

		verify(bookService, times(1)).update(eq(1L), any(Book.class));
	}

	@Test
	public void testDeleteBook() throws Exception {
		doNothing().when(bookService).delete(1L);

		mockMvc.perform(delete("/books/1"))
				.andExpect(status().isOk());

		verify(bookService, times(1)).delete(1L);
	}
	@Test
	void contextLoads() {
	}

}
