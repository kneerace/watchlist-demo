package com.openclassrooms.watchlist.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieRatingService {

    String apiUrl = "http://www.omdbapi.com/?apikey=cc9bf9ef&t=";

    public String getMovieRating(String title) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ObjectNode> response = restTemplate.getForEntity(apiUrl + title, ObjectNode.class);
            ObjectNode jsonObject = response.getBody();

            return jsonObject.path("imdbRating").asText();
        } catch (Exception e) {
            System.out.println("Something went wrong while calling imdb api " + e.getMessage());
            return null;
        } // end try catch
    } // end getMovieRating
}
