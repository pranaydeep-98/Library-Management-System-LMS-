package com.library.library_management.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.library.library_management.repository.BookRepository;
import com.library.library_management.repository.IssueRepository;
import com.library.library_management.repository.UserRepository;
import com.library.library_management.model.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> getLibraryStats() {
        List<Book> allBooks = bookRepository.findAll();

        long totalBooks = allBooks.stream().mapToInt(Book::getQuantity).sum();
        long availableBooks = allBooks.stream().mapToInt(Book::getAvailableCopies).sum();
        long issuedBooks = totalBooks - availableBooks;
        long totalUsers = userRepository.count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalBooks", totalBooks);
        stats.put("availableBooks", availableBooks);
        stats.put("issuedBooks", issuedBooks);
        stats.put("totalUsers", totalUsers);

        return stats;
    }
}
