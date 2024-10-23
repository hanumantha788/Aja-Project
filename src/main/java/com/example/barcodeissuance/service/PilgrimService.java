package com.example.barcodeissuance.service;

import com.example.barcodeissuance.DTOs.Request;
import com.example.barcodeissuance.DTOs.Response;
import com.example.barcodeissuance.Entity.EntryQueues;
import com.example.barcodeissuance.Entity.Pilgrim;
import com.example.barcodeissuance.repository.EntryQueuesRepo;
import com.example.barcodeissuance.repository.PilgrimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class PilgrimService {

    @Autowired
    private PilgrimRepository pilgrimRepository;

    @Autowired
    private EntryQueuesRepo entryQueuesRepo;

    public Response save(Request pilgrimRequest) {

        Pilgrim pilgrim = new Pilgrim();
        pilgrim.setName(pilgrimRequest.getName());
        pilgrim.setAge(pilgrimRequest.getAge());
        pilgrim.setGender(pilgrimRequest.getGender());
        pilgrim.setAdhaarNo(pilgrimRequest.getAdhaarNo());

        Random random = new Random();
        long barcode = 1000000000L + (long) (random.nextDouble() * 9000000000L); // Generates a number between 1000000000 and 9999999999
        pilgrim.setBarcode(barcode);

        pilgrim.setCreatedDate(LocalDateTime.now());
        pilgrimRepository.save(pilgrim);
        Response response = new Response();
        response.setCreatedDate(pilgrim.getCreatedDate());
        response.setBarcode(pilgrim.getBarcode());
        return response;
    }

    public String validateBarcode(Long barcode) {
        Optional<Pilgrim> pilgrimOptional = pilgrimRepository.findByBarcode(barcode);
        if (pilgrimOptional.isPresent()) {
            Pilgrim pilgrim = pilgrimOptional.get();

            // Check if the barcode has been scanned
            if (pilgrim.isScanned()) {
                return "This barcode has already been scanned.";
            }

            // Check if the barcode is still valid (12 hours)
            if (pilgrim.getCreatedDate().plusHours(12).isBefore(LocalDateTime.now())) {
                return "This barcode has expired.";
            }

            // Mark the barcode as scanned
            pilgrim.setScanned(true);
            pilgrimRepository.save(pilgrim);

            // Proceed with queuing logic
            EntryQueues entryQueue = new EntryQueues();
            entryQueue.setBarcode(barcode);
            entryQueue.setTime(LocalDateTime.now());
            // Set entry times as required
            entryQueuesRepo.save(entryQueue);

            return "Barcode is valid. You may proceed.";
        } else {
            return "Invalid barcode.";
        }
    }

    public Map<String, Long> generateDailyReport() {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = LocalDateTime.now().toLocalDate().atTime(23, 59, 59);


        long totalIssued = pilgrimRepository.countByCreatedDateBetween(startOfDay, endOfDay);


        long totalEntered = pilgrimRepository.countByScannedTrueAndCreatedDateBetween(startOfDay, endOfDay);

        long expiredCount = pilgrimRepository.countByCreatedDateBeforeAndScannedFalse(LocalDateTime.now().minusHours(12));

        // Prepare a map to hold the counts
        Map<String, Long> report = new HashMap<>();
        report.put("totalTokensIssued", totalIssued);
        report.put("totalPilgrimsEntered", totalEntered);
        report.put("totalExpiredTokens", expiredCount);

        return report;
    }
}


