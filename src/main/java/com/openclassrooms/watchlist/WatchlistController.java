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

   /* @GetMapping("/watchlistItemForm")
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
    } // end showWatchlistItemForm*/

    @GetMapping("/watchlistItemForm")
    public ModelAndView showWatchlistItemForm(@RequestParam(required = false) Integer id) {
        ModelAndView mav = new ModelAndView("watchlistItemForm");  // creating modelandview
        WatchlistItem watchlistItem;
        if (id != null) {
            watchlistItem = findWatchlistItemById(id);
            if(watchlistItem == null) {
                return new ModelAndView("watchlistItemForm");
            }}
        else {
            watchlistItem = new WatchlistItem();
        }
        mav.addObject("watchlistItem", watchlistItem);
        return mav;
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

        if (bindingResult.hasErrors()) {
           /* System.out.println("BindingResult Errors:");
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println("Field: " + error.getField());
                System.out.println("Error: " + error.getDefaultMessage());  // Get the message
                System.out.println("Rejected Value: " + error.getRejectedValue()); // See what was submitted
                System.out.println("--------------------");
            }*/
            ModelAndView mav = new ModelAndView("watchlistItemForm");
            mav.addObject("watchlistItem", watchlistItem);
            return mav;
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

} // end  class
