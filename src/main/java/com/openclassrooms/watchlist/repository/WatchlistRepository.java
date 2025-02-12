package com.openclassrooms.watchlist.repository;

import com.openclassrooms.watchlist.domain.WatchlistItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WatchlistRepository {

    private List<WatchlistItem> watchlistItems = new ArrayList<WatchlistItem>();
    private static int index = 1;

    public List<WatchlistItem> getWatchlistItems() {
        return watchlistItems;
    }

    public void addItem(WatchlistItem watchlistItem) {
        watchlistItem.setId(index++);
        watchlistItems.add(watchlistItem);
    }

    public WatchlistItem findWatchlistItemById(Integer id) {
        for (WatchlistItem watchlistitem : watchlistItems) {
            if (watchlistitem.getId().equals(id)) {
                return watchlistitem;
            } // end if
        } // end for
        return null;
    }

    public WatchlistItem findWatchlistItemByTitle(String title) {
        for (WatchlistItem watchlistitem : watchlistItems) {
            if (watchlistitem.getTitle().equals(title)) {
                return watchlistitem;
            } // end if
        } // end for
        return null;
    }

}
