package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.impl.RewardAsyncServiceImpl;
import com.example.demo.util.RewardCalculator;

class RewardAsyncServiceImplTest {

	@Test
	void shouldCalculateRewardsAsync() throws Exception {

		TransactionRepository repo = mock(TransactionRepository.class);
		RewardCalculator calculator = new RewardCalculator();

		RewardAsyncServiceImpl service = new RewardAsyncServiceImpl(repo, calculator);

		Transaction txn = new Transaction();
		txn.setTxnId(1L);
		txn.setCustomerId(1L);
		txn.setAmount(120);
		txn.setTxnDate(LocalDate.of(2025, 1, 10));

		when(repo.findByCustomerIdAndTxnDateBetween(anyLong(), any(), any())).thenReturn(List.of(txn));

		CompletableFuture<?> future = service.calculateRewardsAsync(1L, LocalDate.of(2025, 1, 1),
				LocalDate.of(2025, 3, 31));

		var response = future.get();

		assertNotNull(response);
	}
}
