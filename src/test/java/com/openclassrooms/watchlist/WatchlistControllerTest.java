package com.openclassrooms.watchlist;

import com.openclassrooms.watchlist.service.WatchlistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ExtendWith(SpringExtension.class)
class WatchlistControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    WatchlistService watchlistService;
    @Test
    void testGetWatchlist() throws Exception {
        mockMvc.perform(get("/watchlist"))
                .andExpect(status().isOk())
                .andExpect(view().name("watchlist"))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("watchlistItems"))
                .andExpect(model().attributeExists("numberOfMovies"));
    }

    @Test
    void testShowWatchlistItemForm() throws Exception {
        mockMvc.perform(get("/watchlistItemForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("watchlistItemForm"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("watchlistItem"));
    } // end testShowWatchlistItemForm

    @Test
    void testSubmitWatchlistItemForm() throws Exception {
        mockMvc.perform(post("/watchlistItemForm")
                        .param("title", "Lion King")
                        .param("rating", "9.7")
                        .param("priority", "H"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/watchlist"));
    }
}