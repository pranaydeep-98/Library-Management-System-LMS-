package com.library.library_management.service;

import org.springframework.stereotype.Service;
import java.util.List;

import com.library.library_management.repository.BookRepository;
import com.library.library_management.model.Book;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {

        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new RuntimeException("Book title cannot be empty");
        }

        // When a new book is added or updated, correctly set available copies if not
        // provided
        if (book.getId() == null) {
            book.setAvailableCopies(book.getQuantity());
        }

        return bookRepository.save(book);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book updateBook(Long id, Book updatedBook) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());
        existing.setCategory(updatedBook.getCategory());
        existing.setIsbn(updatedBook.getIsbn());

        // Adjust available copies proportionally when quantity changes
        int oldQuantity = existing.getQuantity();
        int newQuantity = updatedBook.getQuantity();
        int borrowed = oldQuantity - existing.getAvailableCopies();
        existing.setQuantity(newQuantity);
        existing.setAvailableCopies(Math.max(0, newQuantity - borrowed));

        return bookRepository.save(existing);
    }

    public List<Book> searchBooks(String keyword) {
        return bookRepository.searchBooks(keyword);
    }

    public void deleteBook(Long id) {

        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found");
        }

        bookRepository.deleteById(id);
    }
}