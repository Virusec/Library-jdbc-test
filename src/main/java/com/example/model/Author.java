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
public class Author {
    private int id;
    private String firstName;
    private String lastName;
}
