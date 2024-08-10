package org.project.book_application.dto;

import java.time.LocalDate;
import java.util.List;

import org.project.book_application.model.BookFormat;

public record BookDetailsResponse(
        int bookId,
        String bookName,
        String authorName,
        int totalPage,
        LocalDate publicationDate,
        BookFormat bookFormat,
        String category,
        List<String> availability,
        String description) {

}
