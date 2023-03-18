package com.five.spring_demo.controller;

import com.five.spring_demo.entity.Book;
import com.five.spring_demo.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookMapper bookMapper;

    public BookController(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @GetMapping("/book")
    public List<Book> query() {
        return bookMapper.selectBook();
    }

    @GetMapping("/book/{isbn}")
    public List<Book> queryByIsbn(@PathVariable String isbn) {
        return bookMapper.selectBookByIsbn(isbn);
    }

    @PutMapping("/book")
    public int update(Book book){
        return bookMapper.updateBook(book);
    }

    @PostMapping("/book")
    public int insert(Book book) {
        return bookMapper.insertBook(book);
    }

    @DeleteMapping("/book/{isbn}")
    public int delete(@PathVariable String isbn) {
        return bookMapper.deleteBook(isbn);
    }
}
