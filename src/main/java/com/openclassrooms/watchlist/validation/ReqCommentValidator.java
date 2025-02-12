package com.openclassrooms.watchlist.validation;

import com.openclassrooms.watchlist.domain.WatchlistItem;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ReqCommentValidator implements ConstraintValidator<ReqComment, WatchlistItem> {

    @Override
    public boolean isValid(WatchlistItem watchlistItem, ConstraintValidatorContext context) {


        return !(Double.valueOf(watchlistItem.getRating()) <= 5 &&
                watchlistItem.getComment().trim().length() < 15);
    } // end isValid
} // end class
