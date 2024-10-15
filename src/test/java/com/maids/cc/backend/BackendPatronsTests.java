package com.maids.cc.backend;

import com.maids.cc.backend.library.controllers.PatronController;
import com.maids.cc.backend.library.entities.Patron;
import com.maids.cc.backend.library.services.PatronService;
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
class BackendPatronsTests {

    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patronController).build();
    }

    @Test
    public void testFindAll() throws Exception {
        List<Patron> patrons = Arrays.asList(
                new Patron("John Doe", "john@example.com"),
                new Patron("Jane Smith", "jane@example.com")
        );

        when(patronService.findAll()).thenReturn(patrons);

        mockMvc.perform(get("/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));

        verify(patronService, times(1)).findAll();
    }

    @Test
    public void testFindById() throws Exception {
        Patron patron = new Patron("John Doe", "john@example.com");

        when(patronService.findById(1L)).thenReturn(patron);

        mockMvc.perform(get("/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(patronService, times(1)).findById(1L);
    }

    @Test
    public void testCreatePatron() throws Exception {
        Patron patron = new Patron("New Patron", "new@example.com");

        when(patronService.create(any(Patron.class))).thenReturn(patron);

        mockMvc.perform(post("/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patron)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Patron"));

        verify(patronService, times(1)).create(any(Patron.class));
    }

    @Test
    public void testUpdatePatron() throws Exception {
        Patron updatedPatron = new Patron("Updated Patron", "updated@example.com");

        when(patronService.update(eq(1L), any(Patron.class))).thenReturn(updatedPatron);

        mockMvc.perform(put("/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPatron)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Patron"));

        verify(patronService, times(1)).update(eq(1L), any(Patron.class));
    }

    @Test
    public void testDeletePatron() throws Exception {
        doNothing().when(patronService).delete(1L);

        mockMvc.perform(delete("/patrons/1"))
                .andExpect(status().isOk());

        verify(patronService, times(1)).delete(1L);
    }

    @Test
    void contextLoads() {
    }
}
