package com.example.demo.model;

import jakarta.persistence.*;

import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Transaction {

	@Id
	private Long txnId;

	private Long customerId;

	private Long productId;

	private double amount;

	private LocalDate txnDate;

}
