package com.example.barcodeissuance.repository;

import com.example.barcodeissuance.Entity.EntryQueues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EntryQueuesRepo extends JpaRepository<EntryQueues,Long> {
    Optional<EntryQueues> findByBarcode(Long barcode);
}
