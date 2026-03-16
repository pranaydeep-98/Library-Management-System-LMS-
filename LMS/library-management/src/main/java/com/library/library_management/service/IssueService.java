package com.library.library_management.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.library.library_management.repository.BookRepository;
import com.library.library_management.repository.IssueRepository;
import com.library.library_management.repository.UserRepository;
import com.library.library_management.model.Book;
import com.library.library_management.model.IssueRecord;
import com.library.library_management.model.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserRepository userRepository;

    public IssueRecord issueBook(Long bookId, String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("Book not available");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        IssueRecord record = new IssueRecord();
        record.setBookId(book.getId());
        record.setUserId(user.getId());
        record.setIssueDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14)); // 14 days borrow period
        record.setStatus("ISSUED");
        record.setFine(0.0);

        return issueRepository.save(record);
    }

    public IssueRecord returnBook(Long issueId) {
        IssueRecord record = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue record not found"));

        if ("RETURNED".equals(record.getStatus())) {
            throw new RuntimeException("Book already returned");
        }

        Book book = bookRepository.findById(record.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        record.setReturnDate(LocalDate.now());
        record.setStatus("RETURNED");

        // Calculate fine if overdue (e.g., $1 per day)
        if (record.getReturnDate().isAfter(record.getDueDate())) {
            long daysLate = ChronoUnit.DAYS.between(record.getDueDate(), record.getReturnDate());
            record.setFine(daysLate * 1.0);
        } else {
            record.setFine(0.0);
        }

        return issueRepository.save(record);
    }

    public List<IssueRecord> getBorrowHistory(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return issueRepository.findByUserId(user.getId());
    }

    public List<IssueRecord> getAllHistory() {
        return issueRepository.findAll();
    }
}