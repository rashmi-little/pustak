package org.project.book_application.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class Book {
    private String bookName;
    private String authorName;
    private Integer totalPage;
    private LocalDate publicationDate;
    private BookFormat bookFormat;
    private String category;
    private List<String> availability;
    private String description;
}
