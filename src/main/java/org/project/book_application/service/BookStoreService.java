package org.project.book_application.service;

import org.project.book_application.dto.BookDetailsResponse;
import org.project.book_application.dto.HomePageResponse;
import org.project.book_application.model.Book;
import org.project.book_application.model.BookFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

import java.util.stream.*;

import lombok.RequiredArgsConstructor;
import lombok.val;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookStoreService {
    private final JdbcTemplate jdbcTemplate;

    public void createBook(Book book, String filePath) {
        String sql = """
                INSERT INTO book_store(book_name, author_name, total_page, publication_date, book_format, category, availability, book_description, image) VALUES (?,?,?,?,?,?,?,?,?)
                """;
        jdbcTemplate.update(sql, book.getBookName(), book.getAuthorName(), book.getTotalPage(),
                book.getPublicationDate(), book.getBookFormat().name(), book.getCategory(),
                listToCSV(book.getAvailability()), book.getDescription(), filePath);
        System.out.println("BOok database insert");
    }

    public List<HomePageResponse> getBooks() {
        var sql = """
                SELECT book_id, book_name FROM book_store;
                """;

        return jdbcTemplate.query(sql, homePageRowMapper());
    }

    private RowMapper<HomePageResponse> homePageRowMapper() {
        return (resultSet, rowNumber) -> {
            int bookId = resultSet.getInt("book_id");
            String bookName = resultSet.getString("book_name");
            return new HomePageResponse(bookId, bookName);
        };
    }

    public BookDetailsResponse getBook(int id) {
        String sql = """
                SELECT * FROM book_store WHERE book_id = ?
                """;
        return jdbcTemplate.queryForObject(sql, getBookDeatilsRowMapper(), id);
    }

    private RowMapper<BookDetailsResponse> getBookDeatilsRowMapper() {
        return (resultSet, rowNumber) -> {
            int bookId = resultSet.getInt("book_id");
            String bookName = resultSet.getString("book_name");
            String authorName = resultSet.getString("author_name");
            int totalPage = resultSet.getInt("total_page");
            String bookFormat = resultSet.getString("book_format");
            String category = resultSet.getString("category");
            String description = resultSet.getString("book_description");
            Date publicationDate = resultSet.getDate("publication_date");
            String availability = resultSet.getString("availability");

            return new BookDetailsResponse(bookId, bookName, authorName, totalPage, convertSqlToLocal(publicationDate),
                    BookFormat.valueOf(bookFormat), category, csvToList(availability), description);
        };
    }

    private LocalDate convertSqlToLocal(Date date) {
        var formatter = new SimpleDateFormat("yyyy-MM-dd");
        return LocalDate.parse(formatter.format(date));
    }

    private String listToCSV(List<String> list) {
        return list.stream().collect(Collectors.joining(","));
    }

    private List<String> csvToList(String value) {
        return Arrays.stream(value.split(",")).toList();
    }

    public String findImageById(int id) {
        String sql = "SELECT image FROM book_store WHERE book_id = ?";
        return jdbcTemplate.queryForObject(sql,(rs, rn) -> rs.getString("image"), id);
    }
}
