package com.openclassrooms.watchlist.validation;

import com.openclassrooms.watchlist.domain.WatchlistItem;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GoodMovieValidator implements ConstraintValidator<GoodMovie, WatchlistItem> {
    @Override
    public boolean isValid(WatchlistItem watchlistItem, ConstraintValidatorContext context) {
        return !(Double.valueOf(watchlistItem.getRating()) > 5
                && "L".equalsIgnoreCase(watchlistItem.getPriority()));
    }
}
