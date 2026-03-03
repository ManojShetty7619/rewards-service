package com.example.demo.service;

import com.example.demo.dto.RewardResponse;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.TransactionNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.model.Transaction;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private RewardService rewardService;

    private Customer customer;

    @BeforeEach
    void setup() {
        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setName("John");
    }

    @Test
    void shouldCalculateRewardsForDateRange() {

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        List<Transaction> transactions = List.of(
                new Transaction(
                        1L,
                        1L,
                        101L,
                        120.0,
                        LocalDate.of(2025, 1, 15))
        );

        when(transactionRepository
                .findByCustomerIdAndTxnDateBetween(
                        1L,
                        LocalDate.of(2025,1,1),
                        LocalDate.of(2025,3,31)))
                .thenReturn(transactions);

        RewardResponse response =
                rewardService.calculateRewards(
                        1L,
                        LocalDate.of(2025,1,1),
                        LocalDate.of(2025,3,31),
                        null);

        assertNotNull(response);
        assertEquals(1L, response.getCustomerId());
    }

    @Test
    void shouldThrowExceptionWhenStartDateAfterEndDate() {

        assertThrows(InvalidRequestException.class, () ->
                rewardService.calculateRewards(
                        1L,
                        LocalDate.of(2025,3,1),
                        LocalDate.of(2025,1,1),
                        null));
    }

    @Test
    void shouldThrowExceptionWhenMonthsAndDateRangeBothProvided() {

        assertThrows(InvalidRequestException.class, () ->
                rewardService.calculateRewards(
                        1L,
                        LocalDate.of(2025,1,1),
                        LocalDate.of(2025,3,1),
                        2));
    }

    @Test
    void shouldThrowExceptionWhenMonthsNegative() {

        assertThrows(InvalidRequestException.class, () ->
                rewardService.calculateRewards(
                        1L,
                        null,
                        null,
                        -1));
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {

        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () ->
                rewardService.calculateRewards(
                        1L,
                        LocalDate.of(2025,1,1),
                        LocalDate.of(2025,3,31),
                        null));
    }

    @Test
    void shouldThrowExceptionWhenNoTransactionsFound() {

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(transactionRepository
                .findByCustomerIdAndTxnDateBetween(
                        1L,
                        LocalDate.of(2025,1,1),
                        LocalDate.of(2025,3,31)))
                .thenReturn(List.of());

        assertThrows(TransactionNotFoundException.class, () ->
                rewardService.calculateRewards(
                        1L,
                        LocalDate.of(2025,1,1),
                        LocalDate.of(2025,3,31),
                        null));
    }
}