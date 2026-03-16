package com.library.library_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.library.library_management.service.IssueService;
import com.library.library_management.model.IssueRecord;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/issue")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @PostMapping("/{bookId}")
    public IssueRecord issueBook(@PathVariable("bookId") Long bookId, Principal principal) {
        return issueService.issueBook(bookId, principal.getName());
    }

    @PostMapping("/return/{issueId}")
    public IssueRecord returnBook(@PathVariable("issueId") Long issueId) {
        return issueService.returnBook(issueId);
    }

    @GetMapping("/history")
    public List<IssueRecord> getMyHistory(Principal principal) {
        return issueService.getBorrowHistory(principal.getName());
    }

    @GetMapping("/all-history")
    public List<IssueRecord> getAllHistory() {
        return issueService.getAllHistory();
    }
}