package com.apica.journal.assignment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.apica.journal.assignment.entity.Journal;
import com.apica.journal.assignment.service.JournalService;

@RestController
@RequestMapping("/api/journals")
public class JournalController {

   
    private final JournalService journalService;
    
    public JournalController(JournalService journalService) {
    	this.journalService = journalService;
    }
    

    @GetMapping
    public ResponseEntity<List<Journal>> getAllJournals() {
        return new ResponseEntity<>(journalService.getAllJournals(), HttpStatus.OK);
    }
}
