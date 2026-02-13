package com.example.demo.service.impl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.dto.RewardResponse;
import com.example.demo.exception.TransactionNotFoundException;
import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.RewardAsyncService;
import com.example.demo.util.RewardCalculator;

@Service
public class RewardAsyncServiceImpl implements RewardAsyncService {

	private final TransactionRepository repository;
	private final RewardCalculator calculator;

	public RewardAsyncServiceImpl(TransactionRepository repository, RewardCalculator calculator) {
		this.repository = repository;
		this.calculator = calculator;
	}

	@Async
	@Override
	public CompletableFuture<RewardResponse> calculateRewardsAsync(Long customerId, LocalDate from, LocalDate to) {

		List<Transaction> transactions = repository.findByCustomerIdAndTxnDateBetween(customerId, from, to);

		if (transactions.isEmpty()) {
			throw new TransactionNotFoundException("No transactions found");
		}

		Map<YearMonth, Integer> monthly = new HashMap<>();
		int total = 0;

		for (Transaction txn : transactions) {
			int points = calculator.calculate(txn.getAmount());
			monthly.merge(YearMonth.from(txn.getTxnDate()), points, Integer::sum);
			total += points;
		}

		RewardResponse response = new RewardResponse(customerId, monthly, total, transactions);

		return CompletableFuture.completedFuture(response);
	}
}
