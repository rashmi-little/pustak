package org.project.book_application.controller;

import org.project.book_application.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookStoreController {
    @GetMapping({"/","/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/new-book")
    public String newBook() {
        return "new-book";
    }
    
    @GetMapping("/details")
    public String details() {
        return "book-details";
    }
    @PostMapping("/create-book")
    public String addBook(@ModelAttribute Book book) {
        System.out.println(book);
        return "new-book";
    }
}
