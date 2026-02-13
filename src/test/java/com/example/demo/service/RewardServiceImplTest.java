package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.impl.RewardServiceImpl;
import com.example.demo.util.RewardCalculator;

class RewardServiceImplTest {

	@Test
	void shouldCalculateRewardsCorrectly() {

		TransactionRepository repo = mock(TransactionRepository.class);
		RewardCalculator calculator = new RewardCalculator();

		RewardServiceImpl service = new RewardServiceImpl(repo, calculator);

		Transaction txn = new Transaction();
		txn.setTxnId(1L);
		txn.setCustomerId(1L);
		txn.setAmount(120);
		txn.setTxnDate(LocalDate.of(2025, 1, 10));

		when(repo.findByCustomerIdAndTxnDateBetween(anyLong(), any(), any())).thenReturn(List.of(txn));

		var response = service.calculateRewards(1L, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 3, 31));

		assertEquals(90, response.getTotalRewards());
		assertFalse(response.getMonthlyRewards().isEmpty());
	}
}
