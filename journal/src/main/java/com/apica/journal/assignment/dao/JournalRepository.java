package com.apica.journal.assignment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apica.journal.assignment.entity.Journal;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {
}
