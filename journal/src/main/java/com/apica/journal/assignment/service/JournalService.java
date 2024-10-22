package com.apica.journal.assignment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.apica.journal.assignment.entity.Journal;
import com.apica.journal.assignment.dao.JournalRepository;

@Service
public class JournalService {

  
    private JournalRepository journalRepository;
    
    public JournalService(JournalRepository journalRepository) {
    	this.journalRepository = journalRepository;
    }

    public List<Journal> getAllJournals() {
        return journalRepository.findAll();
    }
}
