package com.example.barcodeissuance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.barcodeissuance.Entity.Pilgrim;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PilgrimRepository extends JpaRepository<Pilgrim, Long> {

    Optional<Pilgrim> findByBarcode(Long barcode);

    long countByCreatedDateBeforeAndScannedFalse(LocalDateTime localDateTime);

    long countByScannedTrueAndCreatedDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    long countByCreatedDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
