package com.spdev.agileboard_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spdev.agileboard_backend.modals.Issue;
import java.util.List;


public interface IssueRepository extends JpaRepository<Issue, Long>  {
 List<Issue> findByProjectID(Long projectID);
}
