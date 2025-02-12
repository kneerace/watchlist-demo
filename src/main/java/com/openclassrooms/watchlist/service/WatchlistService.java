package com.openclassrooms.watchlist.service;

import com.openclassrooms.watchlist.domain.WatchlistItem;
import com.openclassrooms.watchlist.exception.DuplicateTitleException;
import com.openclassrooms.watchlist.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistService {

//    @Autowired  // this is field injection
    WatchlistRepository watchlistRepository;
//    @Autowired
    MovieRatingService movieRatingService;

    @Autowired // this is constructor injection
    public WatchlistService(WatchlistRepository watchlistRepository, MovieRatingService movieRatingService) {
        this.watchlistRepository = watchlistRepository;
        this.movieRatingService = movieRatingService;
    }

    public List<WatchlistItem> getWatchlistItems() {

//        return watchlistRepository.getWatchlistItems();
        List<WatchlistItem> watchlistItems = watchlistRepository.getWatchlistItems();
        for (WatchlistItem watchlistItem : watchlistItems) {
            String rating = movieRatingService.getMovieRating(watchlistItem.getTitle());

            if (rating !=null){
                watchlistItem.setRating(rating);
            } // end if
        } // end for

        return watchlistItems;
    } // end getWatchlistItems

    public int getWatchlistItemSize() {
        return watchlistRepository.getWatchlistItems().size();
    } // end getWatchlistItemSize

    public WatchlistItem findWatchlistItemById(Integer id) {
        return watchlistRepository.findWatchlistItemById(id);
    } // end findWatchlistItemById

    public void addOrUpdateWatchlistItem(WatchlistItem watchlistItem) throws DuplicateTitleException {
        WatchlistItem existingRepoItem = watchlistRepository.findWatchlistItemById(watchlistItem.getId());

        if(existingRepoItem == null) {
            if (watchlistRepository.findWatchlistItemByTitle(watchlistItem.getTitle()) != null) {
                throw new DuplicateTitleException();
            }
            watchlistRepository.addItem(watchlistItem);
        } else{
            existingRepoItem.setComment(watchlistItem.getComment());
            existingRepoItem.setPriority(watchlistItem.getPriority());
            existingRepoItem.setRating(watchlistItem.getRating());
            existingRepoItem.setTitle(watchlistItem.getTitle());
        }
    } // end addOrUpdateWatchlistItem

}
