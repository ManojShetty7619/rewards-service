package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.RewardResponse;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.TransactionNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.model.Transaction;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class RewardServiceImplTest {

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private RewardService rewardService;

	@Test
	void shouldCalculateRewardsSuccessfully() {

		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setName("Pooja Pachore");

		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

		Transaction txn = new Transaction();
		txn.setTxnId(1L);
		txn.setAmount(120);
		txn.setTxnDate(LocalDate.of(2025, 1, 10));

		when(transactionRepository.findByCustomerIdAndTxnDateBetween(anyLong(), any(), any())).thenReturn(List.of(txn));

		RewardResponse response = rewardService.calculateRewards(1L, LocalDate.of(2025, 1, 1),
				LocalDate.of(2025, 3, 31));

		assertEquals(1L, response.getCustomerId());
		assertFalse(response.getMonthlyRewards().isEmpty());
	}

	@Test
	void shouldThrowExceptionWhenStartDateAfterEndDate() {

		assertThrows(InvalidRequestException.class,
				() -> rewardService.calculateRewards(1L, LocalDate.of(2025, 5, 1), LocalDate.of(2025, 3, 1)));
	}

	@Test
	void shouldThrowExceptionWhenCustomerNotFound() {

		when(customerRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(TransactionNotFoundException.class,
				() -> rewardService.calculateRewards(1L, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 3, 31)));
	}

	@Test
	void shouldThrowExceptionWhenNoTransactionsFound() {

		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setName("John");

		when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

		when(transactionRepository.findByCustomerIdAndTxnDateBetween(anyLong(), any(), any()))
				.thenReturn(Collections.emptyList());

		assertThrows(TransactionNotFoundException.class,
				() -> rewardService.calculateRewards(1L, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 3, 31)));
	}
}
