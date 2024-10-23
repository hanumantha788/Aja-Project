package com.example.barcodeissuance.controller;

import com.example.barcodeissuance.DTOs.Request;
import com.example.barcodeissuance.DTOs.Response;
import com.example.barcodeissuance.service.EntryQueueService;
import com.example.barcodeissuance.service.PilgrimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/barcode")
public class PilgrimController {


    @Autowired
    private PilgrimService pilgrimService;

    @Autowired
    private EntryQueueService entryQueueService;

    @PostMapping("/save")

    public Response savePilgrim(@RequestBody Request request){

      return pilgrimService.save(request);
    }
//    @GetMapping("/validate/{uuid}")
//    public ResponseEntity<String> validateBarcode(@PathVariable Long uuid) {
//        boolean isValid = pilgrimService.validateBarcode(uuid);
//        return ResponseEntity.ok(isValid ? "Valid Barcode" : "Invalid or Expired Barcode");
//    }
//    @GetMapping("/check-queue/{barcode}/{queue}")
//    public ResponseEntity<String> checkQueue(@PathVariable Long barcode, @PathVariable int queue) {
//        String result = pilgrimService.checkAndMoveToNextQueue(barcode, queue);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
@GetMapping("/validate/{barcode}")
public ResponseEntity<String> validateBarcode(@PathVariable Long barcode) {
    String responseMessage = pilgrimService.validateBarcode(barcode);
    return ResponseEntity.ok(responseMessage);
}
    @PostMapping("/enter-queue/{barcode}/{queueNumber}")
    public ResponseEntity<String> enterQueue(@PathVariable Long barcode, @PathVariable int queueNumber) {
        try {
            String response = entryQueueService.enterQueue(barcode, queueNumber);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/daily")
    public ResponseEntity<Map<String, Long>> getDailyReport() {
        Map<String, Long> report = pilgrimService.generateDailyReport();
        return ResponseEntity.ok(report);
    }

}
