package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author Anatoliy Shikin
 */
@Data
@AllArgsConstructor
@Builder
public class Book {
    private int id;
    private String title;
    private int authorId;
    private int publishedYear;
    private int totalCopies;
    private int borrowedCount;
    private Author author;
}
