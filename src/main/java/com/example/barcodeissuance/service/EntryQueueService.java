package com.example.barcodeissuance.service;

import com.example.barcodeissuance.Entity.EntryQueues;
import com.example.barcodeissuance.repository.EntryQueuesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EntryQueueService {

    @Autowired
    private EntryQueuesRepo entryQueuesRepository;

    public String enterQueue(Long barcode, int queueNumber) {
        Optional<EntryQueues> entryQueueOptional = entryQueuesRepository.findById(barcode);
        if (!entryQueueOptional.isPresent()) {
            throw new IllegalArgumentException("Invalid barcode.");
        }

        EntryQueues entryQueue = entryQueueOptional.get();

        // Ensure the pilgrim is entering the queues in the correct sequence
        switch (queueNumber) {
            case 1:
                if (!entryQueue.isQ1EntryTime()) {
                    entryQueue.setQ1EntryTime(true);
                    entryQueue.setTime(LocalDateTime.now());
                } else {
                    throw new IllegalArgumentException("You have already entered Queue 1.");
                }
                break;
            case 2:
                if (!entryQueue.isQ1EntryTime()) {
                    throw new IllegalArgumentException("You must enter Queue 1 first.");
                } else if (!entryQueue.isQ2EntryTime()) {
                    entryQueue.setQ2EntryTime(true);
                    entryQueue.setTime(LocalDateTime.now());
                } else {
                    throw new IllegalArgumentException("You have already entered Queue 2.");
                }
                break;
            case 3:
                if (!entryQueue.isQ2EntryTime()) {
                    throw new IllegalArgumentException("You must enter Queue 2 first.");
                } else if (!entryQueue.isQ3EntryTime()) {
                    entryQueue.setQ3EntryTime(true);
                    entryQueue.setTime(LocalDateTime.now());
                } else {
                    throw new IllegalArgumentException("You have already entered Queue 3.");
                }
                break;
            case 4:
                if (!entryQueue.isQ3EntryTime()) {
                    throw new IllegalArgumentException("You must enter Queue 3 first.");
                } else if (!entryQueue.isQ4EntryTime()) {
                    entryQueue.setQ4EntryTime(true);
                    entryQueue.setTime(LocalDateTime.now());
                } else {
                    throw new IllegalArgumentException("You have already entered Queue 4.");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid queue number.");
        }

        entryQueuesRepository.save(entryQueue);
        return "You have successfully entered Queue " + queueNumber + ".";
    }
}
