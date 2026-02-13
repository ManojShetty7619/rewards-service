package com.example.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    private Long productId;
    private String productName;
    private double price;
}
