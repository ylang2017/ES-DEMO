package com.example.es.service;

import com.example.es.entity.Book;
import com.example.es.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ElasticsearchRestTemplate template;

    public void bulkAddBook(List<Book> bookList) throws IOException {
        bookRepository.bulkInsert(bookList);
    }

    public void addBookIndex() throws IOException {
        bookRepository.addIndex();
    }

    public void addBook(Book book){
        template.save(book);
    }
}
