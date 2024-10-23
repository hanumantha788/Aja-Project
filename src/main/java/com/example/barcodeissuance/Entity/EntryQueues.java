package com.example.barcodeissuance.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntryQueues
{
    @Id
     private Long barcode;
    private LocalDateTime  time;
    private boolean q1EntryTime;
    private boolean q2EntryTime;
    private boolean q3EntryTime;
    private boolean q4EntryTime;
}
