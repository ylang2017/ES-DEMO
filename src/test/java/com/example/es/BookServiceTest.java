package com.example.es;

import com.example.es.entity.Book;
import com.example.es.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class BookServiceTest {
    @Autowired
    BookService bookService;

    @Test
    public void testBulkInsert() throws IOException {
        List<Book> bookList = new ArrayList<>();
        for(int i=0;i<10;i++){
            Book b = new Book(UUID.randomUUID().toString(),"book_"+i,"jack",12.5,"时间简史-我爱我的祖国-富强民主文明和谐，爱国敬业诚信友善",
                    "时间简史-我爱我的祖国-富强民主文明和谐，爱国敬业诚信友善",new Date(),i>5);
            bookList.add(b);
        }
        bookService.bulkAddBook(bookList);
    }

    @Test
    public void testAddBookIndex() throws IOException {
        bookService.addBookIndex();
    }
}
