package com.example.demo.service.impl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.dto.RewardResponse;
import com.example.demo.exception.TransactionNotFoundException;
import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.RewardService;
import com.example.demo.util.RewardCalculator;

@Service
public class RewardServiceImpl implements RewardService {

	private final TransactionRepository repository;
	private final RewardCalculator calculator;

	public RewardServiceImpl(TransactionRepository repository, RewardCalculator calculator) {
		this.repository = repository;
		this.calculator = calculator;
	}

	@Override
	public RewardResponse calculateRewards(Long customerId, LocalDate from, LocalDate to) {

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

		return new RewardResponse(customerId, monthly, total, transactions);
	}
}
