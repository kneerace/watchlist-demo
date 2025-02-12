package com.openclassrooms.watchlist.service;


import com.openclassrooms.watchlist.domain.WatchlistItem;
import com.openclassrooms.watchlist.exception.DuplicateTitleException;
import com.openclassrooms.watchlist.repository.WatchlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WatchlistServiceTest {

    @Mock
    private MovieRatingService movieRatingServiceMock;
    @Mock
    private WatchlistRepository watchlistRepositoryMock;
    @InjectMocks
    private WatchlistService watchlistService;

    @Test
    void testGetWatchlistItems_ReturnedAllItemsFromRepository() {
        //Arrange
        WatchlistItem item1 = new WatchlistItem("Lion King", "9.7", "high", "Simbha", 1);
        WatchlistItem item2 = new WatchlistItem("This King", "8.7", "high", "funny one", 2);
        List<WatchlistItem> watchlistItems = Arrays.asList(item1, item2);

        when(watchlistRepositoryMock.getWatchlistItems()).thenReturn(watchlistItems);
        //Act
        List<WatchlistItem> result = watchlistService.getWatchlistItems();
        //Assert
        assertTrue(result.containsAll(watchlistItems));
        assertTrue(result.get(0).getTitle().equals("Lion King"));
        assertTrue(result.get(1).getTitle().equals("This King"));
    }

    @Test
    void testGetWatchlistItems_RatingOverrideFromOMDBAPICall(){
        //Arrange
        WatchlistItem item1 = new WatchlistItem("Lion King", "9.7", "high", "Simbha", 1);
        List<WatchlistItem> watchlistItems = Arrays.asList(item1);

        when(watchlistRepositoryMock.getWatchlistItems()).thenReturn(watchlistItems);
        when(movieRatingServiceMock.getMovieRating(any(String.class))).thenReturn("10.0");

        //Act
        List<WatchlistItem> result = watchlistService.getWatchlistItems();
        //Assert
        assertTrue(result.get(0).getRating().equals("10.0"));
    }
    @Test
    void testGetWatchlistItemSize() {
        //Arrange
        WatchlistItem item1 = new WatchlistItem("Lion King", "9.7", "high", "Simbha", 1);
        WatchlistItem item2 = new WatchlistItem("This King", "8.7", "high", "funny one", 2);
        List<WatchlistItem> watchlistItems = Arrays.asList(item1, item2);

        Mockito.when(watchlistRepositoryMock.getWatchlistItems()).thenReturn(watchlistItems);
        //Act
        List<WatchlistItem> result = watchlistService.getWatchlistItems();
        //Assert
        assertTrue(result.size() == 2);
    }

    @Test
    void findWatchlistItemById_shouldReturnItem_whenIdExists() {
        // Arrange
        Integer id = 1;
        WatchlistItem item1 = new WatchlistItem("Lion King", "9.7", "high", "Simbha", id);
        WatchlistItem item2 = new WatchlistItem("This King", "8.7", "high", "funny one", id+1);
        List<WatchlistItem> watchlistItems = Arrays.asList(item1, item2);


        when(watchlistRepositoryMock.findWatchlistItemById(any(Integer.class))).thenReturn(watchlistItems.get(0)); // Mock the repository

        // Act
        WatchlistItem actualItem = watchlistService.findWatchlistItemById(id);

        // Assert
        assertNotNull(actualItem);
        assertEquals(watchlistItems.get(0).getTitle(), actualItem.getTitle()); // Assuming WatchlistItem has a getTitle() method
    }

    @Test
    void findWatchlistItemById_shouldReturnNull_whenIdDoesNotExist() {
        // Arrange
        Integer id = 4; // An ID that doesn't exist in the mock data
        WatchlistItem item1 = new WatchlistItem("Lion King", "9.7", "high", "Simbha", id);
        WatchlistItem item2 = new WatchlistItem("This King", "8.7", "high", "funny one", id+1);
        List<WatchlistItem> watchlistItems = Arrays.asList(item1, item2);

        when(watchlistRepositoryMock.findWatchlistItemById(id)).thenReturn(null);

        // Act
        WatchlistItem actualItem = watchlistService.findWatchlistItemById(id);

        // Assert
        assertNull(actualItem);
    }



    @Test
    void findWatchlistItemById_shouldReturnNull_whenWatchlistIsEmpty() {
        // Arrange
        Integer id = 1;
        when(watchlistRepositoryMock.findWatchlistItemById(id)).thenReturn(null);

        // Act
        WatchlistItem actualItem = watchlistService.findWatchlistItemById(id);

        // Assert
        assertNull(actualItem);
    }

    //----------------

    @Test
    void addOrUpdateWatchlistItem_shouldAdd_whenItemDoesNotExist() throws  DuplicateTitleException {
        // Arrange
//        WatchlistItem newItem = new WatchlistItem(1, "New Movie", "Comment", 1, 9);  // Use a constructor that sets all fields
        WatchlistItem newItem = new WatchlistItem("Lion King", "9.7", "high", "Simbha", 9);

        when(watchlistRepositoryMock.findWatchlistItemById(newItem.getId())).thenReturn(null);
        when(watchlistRepositoryMock.findWatchlistItemByTitle(newItem.getTitle())).thenReturn(null);

        // Act
        watchlistService.addOrUpdateWatchlistItem(newItem);

        // Assert
        verify(watchlistRepositoryMock).addItem(newItem); // Verify that addItem was called
    }


    @Test
    void addOrUpdateWatchlistItem_shouldUpdate_whenItemExists() throws DuplicateTitleException {
        // Arrange
        Integer id = 1;
        WatchlistItem existingItem = new WatchlistItem("Lion King", "9.7", "high", "Simbha", 9);
        WatchlistItem updatedItem = new WatchlistItem("Lion Queen", "9.7", "high", "Simbha", 9);
        when(watchlistRepositoryMock.findWatchlistItemById(any(Integer.class))).thenReturn(existingItem);

        // Act
        watchlistService.addOrUpdateWatchlistItem(updatedItem);

        // Assert
        assertEquals(updatedItem.getComment(), existingItem.getComment());
        assertEquals(updatedItem.getPriority(), existingItem.getPriority());
        assertEquals(updatedItem.getRating(), existingItem.getRating());
        assertEquals(updatedItem.getTitle(), existingItem.getTitle());

        verify(watchlistRepositoryMock, never()).addItem(any()); // Verify that addItem was NOT called
    }
/*
    @Test
    void addOrUpdateWatchlistItem_shouldThrowException_whenDuplicateTitle() {
        // Arrange
        WatchlistItem newItem = new WatchlistItem(1, "Duplicate Movie", "Comment", 1, "9.0");

        when(watchlistRepository.findWatchlistItemById(newItem.getId())).thenReturn(null);
        when(watchlistRepository.findWatchlistItemByTitle(newItem.getTitle())).thenReturn(new WatchlistItem()); // Return a non-null item to simulate a duplicate title

        // Act & Assert (combined using assertThrows)
        assertThrows(DuplicateTitleException.class, () -> {
            watchlistService.addOrUpdateWatchlistItem(newItem);
        });

        verify(watchlistRepository, never()).addItem(any()); // Verify that addItem was NOT called
    }


    @Test
    void addOrUpdateWatchlistItem_shouldAdd_whenIdIsNull() throws DuplicateTitleException {
        // Arrange
        WatchlistItem newItem = new WatchlistItem(null, "New Movie", "Comment", 1, "9.0");  // Use a constructor that sets all fields

        when(watchlistRepository.findWatchlistItemById(null)).thenReturn(null);
        when(watchlistRepository.findWatchlistItemByTitle(newItem.getTitle())).thenReturn(null);

        // Act
        watchlistService.addOrUpdateWatchlistItem(newItem);

        // Assert
        verify(watchlistRepository).addItem(newItem); // Verify that addItem was called
    }

     */
}