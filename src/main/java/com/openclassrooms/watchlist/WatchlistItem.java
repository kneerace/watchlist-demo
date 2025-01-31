package com.openclassrooms.watchlist;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

//import javax.validation.constraints.NotBlank;

public class WatchlistItem {
    @NotBlank(message = "Title is required")
    private String title;
    private String rating;
    private String priority;

    @Size(max = 50, message= "Comment should be maximum of 50 characters")
    private String comment;
    private Integer id;

    public WatchlistItem(String title, String rating, String priority, String comment, Integer id) {
        this.title = title;
        this.rating = rating;
        this.priority = priority;
        this.comment = comment;
        this.id = id;
    } // end constructor

    public WatchlistItem(){};

    public String getTitle() {
        return title;
    }
    public String getRating() {
        return rating;
    }
    public String getPriority() {
        return priority;
    }
    public String getComment() {
        return comment;
    }
    public Integer getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setId(Integer id) {
        this.id = id;
    }
} // end class
