package com.openclassrooms.watchlist;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WatchlistController {

    private List<WatchlistItem> watchlistItems = new ArrayList<WatchlistItem>();
    private static int index = 1;
    @GetMapping("/watchlist")
    public ModelAndView getWatchlist() {
//        watchlistitems.clear();
//        watchlistitems.add(new Watchlistitem("Lion King", "9.7", "high", "Simbha", index++));
//        watchlistitems.add(new Watchlistitem("Wall-E", "9.8", "high", "Space Robots", index++));
//        watchlistitems.add(new Watchlistitem("Shrek", "9.2", "medium", "Green Monster", index++));
//        watchlistitems.add(new Watchlistitem("Monster University", "7.2", "low", "Monster Be-aware", index++));
//        watchlistitems.add(new Watchlistitem("Jaguar", "8.2", "medium", "Animal World", index++));

        String viewName = "watchlist";
        Map<String, Object> model = new HashMap<String, Object>();
//        System.out.println(watchlistitems); //--------
        model.put("watchlistItems", watchlistItems);
        model.put("numberOfMovies", watchlistItems.size());
        return new ModelAndView(viewName, model);
    }

    @GetMapping("/watchlistItemForm")
    public ModelAndView showWatchlistItemForm(@RequestParam(required = false) Integer id) {

        String viewName = "watchlistItemForm";
        Map<String,Object> model = new HashMap<String,Object>();
        WatchlistItem watchlistitem = findWatchlistItemById(id);
        if(watchlistitem != null) {
            model.put("watchlistItem", watchlistitem);
        }
        else {
            model.put("watchlistItem", new WatchlistItem());
        }
        return new ModelAndView(viewName,model);
    } // end showWatchlistItemForm

    private WatchlistItem findWatchlistItemById(Integer id) {
            for (WatchlistItem watchlistitem : watchlistItems) {
                if (watchlistitem.getId().equals(id)) {
                    return watchlistitem;
                } // end if
            } // end for
            return null;
    } // end findWatchlistItemById

    @PostMapping("/watchlistItemForm")
    public ModelAndView submitWatchlistItemForm(@Validated WatchlistItem watchlistItem, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return new ModelAndView("watchlistItemForm");
        }
        WatchlistItem existingWatchlistItem = findWatchlistItemById(watchlistItem.getId());
        if (existingWatchlistItem == null) {
            watchlistItem.setId(index++);
            watchlistItems.add(watchlistItem);
        }
        else {
            existingWatchlistItem.setTitle(watchlistItem.getTitle());
            existingWatchlistItem.setRating(watchlistItem.getRating());
            existingWatchlistItem.setPriority(watchlistItem.getPriority());
            existingWatchlistItem.setComment(watchlistItem.getComment());
        }

        RedirectView redirect = new RedirectView();
        redirect.setUrl("/watchlist");
        return new ModelAndView(redirect);

    } // end submitWatchlistItemForm

    /*@PostMapping("/watchlistItemForm")
    public ModelAndView submitWatchlistItemForm(@Validated Watchlistitem watchlistItem, BindingResult bindingResult) {

//        logger.info("HTTP POST request received at /watchlistItemForm URL ");

        if (bindingResult.hasErrors()) {
            return new ModelAndView("watchlistItemForm");
        }

        try {
            watchlistService.addOrUpdateWatchlistItem(watchlistItem);

        } catch (DuplicateTitleException e) {
            bindingResult.rejectValue("title", "", "This title already exists on your watchlist");
            return new ModelAndView("watchlistItemForm");
        }
        RedirectView redirect = new RedirectView();
        redirect.setUrl("/watchlist");
        return new ModelAndView(redirect);
    }*/


}
