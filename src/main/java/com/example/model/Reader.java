package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Anatoliy Shikin
 */
@Data
@AllArgsConstructor
public class Reader {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
}
