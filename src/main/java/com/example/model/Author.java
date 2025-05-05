package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Anatoliy Shikin
 */
@Data
@AllArgsConstructor
public class Author {
    private int id;
    private String firstName;
    private String lastName;
}
