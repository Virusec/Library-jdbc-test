package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Anatoliy Shikin
 */
@Data
@AllArgsConstructor
public class Borrowing {
    private int id;
    private int readerId;
    private int bookId;
    private LocalDate borrowedDate;
    private LocalDate returnedDate;
}
