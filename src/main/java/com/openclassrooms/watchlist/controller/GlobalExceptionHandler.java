package com.openclassrooms.watchlist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice  // Important!
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public String defaultErrorHandler(HttpServletRequest req, Exception e, Model model) {
        System.err.println("Error occurred: " + e.getMessage());

        model.addAttribute("errorMessage", e.getMessage()); // Or a user-friendly message
        model.addAttribute("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value()); // Or get the correct status
        model.addAttribute("errorDetails", req.getRequestURL()); // Add request details if needed

        return "error"; // Name of your error view (error.html or error.thymeleaf)
    }

    // Example of handling a specific exception (if needed):
    // @ExceptionHandler(ResourceNotFoundException.class)  // If you have such an exception
    // public String resourceNotFoundHandler(HttpServletRequest req, ResourceNotFoundException e, Model model) { ... }
}
