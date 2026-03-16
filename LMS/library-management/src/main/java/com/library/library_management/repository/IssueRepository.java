package com.library.library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.library.library_management.model.IssueRecord;
import java.util.List;

public interface IssueRepository extends JpaRepository<IssueRecord, Long> {
    List<IssueRecord> findByUserId(Long userId);

    List<IssueRecord> findByStatus(String status);
}