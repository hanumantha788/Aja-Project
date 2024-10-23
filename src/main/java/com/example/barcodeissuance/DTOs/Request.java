package com.example.barcodeissuance.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    private String name;
    private String gender;
    private Long adhaarNo;
    private int age;
}

