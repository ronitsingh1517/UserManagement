package com.apica.journal.assignment.component;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.apica.journal.assignment.entity.Journal;

import com.apica.journal.assignment.dao.JournalRepository;

@Component
public class KafkaJournalConsumer {

    private final JournalRepository journalRepository;
    
    public KafkaJournalConsumer(JournalRepository journalRepository) {
    	this.journalRepository = journalRepository;
    }

    @KafkaListener(topics = "user-events", groupId = "journal-group")
    public void consumeUserEvents(String message) {
        Journal journal = new Journal( message, LocalDateTime.now());
        journalRepository.save(journal);
        System.out.println("Consumed message: " + message);
    }
}
