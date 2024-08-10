package org.project.book_application.controller;

import org.project.book_application.model.Book;
import org.project.book_application.service.BookStoreService;
import org.project.book_application.service.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BookStoreController {
    private final BookStoreService bookStoreService;
    private final FileService fileService;
    @GetMapping({"/","/home"})
    public String home(Model model) {
        model.addAttribute("books", bookStoreService.getBooks());
        return "home";
    }

    @GetMapping("/new-book")
    public String newBook() {
        return "new-book";
    }
    
    @GetMapping("/details")
    public String details(@RequestParam int id, Model model) {
        var response = bookStoreService.getBook(id);
        model.addAttribute("book", response);
        return "book-details";
    }
    @PostMapping("/create-book")
    public String addBook(@ModelAttribute Book book) throws Exception{
        String path = fileService.uploadImage(book.getBookImage());
        bookStoreService.createBook(book, path);
        return "redirect:/";
    }

    @GetMapping(path="/image/{id}", produces = {"image/jpeg", "image/jpg", "image/png"})
    @ResponseBody
    public byte[] getImage(@PathVariable int id) throws Exception{
        String imageName = bookStoreService.findImageById(id);
        byte[] image = fileService.getImage(imageName);
        return image;
    }
    
}
