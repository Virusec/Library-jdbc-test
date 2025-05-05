package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Anatoliy Shikin
 */
@Data
@AllArgsConstructor
public class Book {
    private int id;
    private String title;
    private int authorId;
    private Integer publishedYear;
    private int totalCopies;
//    private int borrowedCount;
}
