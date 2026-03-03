package com.example.demo.service;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.MonthlyRewardSummary;
import com.example.demo.dto.RewardResponse;
import com.example.demo.dto.TransactionDTO;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.TransactionNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.model.Transaction;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.RewardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RewardService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;

    public RewardResponse calculateRewards(Long customerId,
                                           LocalDate startDate,
                                           LocalDate endDate,
                                           Integer months) {

        log.info("Starting reward calculation");

        if (months != null && (startDate != null || endDate != null)) {
            throw new InvalidRequestException(
                    "Provide either months or startDate & endDate, not both");
        }

        if (startDate != null && endDate != null) {
            if (startDate.isAfter(endDate)) {
                throw new InvalidRequestException("From date cannot be after end date");
            }
        }

        else if (months != null) {
            if (months <= 0) {
                throw new InvalidRequestException("Months must be greater than 0");
            }
            endDate = LocalDate.now();
            startDate = endDate.minusMonths(months);
        }

        else {
            endDate = LocalDate.now();
            startDate = endDate.minusMonths(3);
        }

        log.info("Final date range used: {} to {}", startDate, endDate);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new TransactionNotFoundException("Customer not found"));

        List<Transaction> transactions =
                transactionRepository
                        .findByCustomerIdAndTxnDateBetween(
                                customerId, startDate, endDate);

        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException("No transactions found");
        }

        Map<String, MonthlyRewardSummary> monthlyRewards = new HashMap<>();

        for (Transaction txn : transactions) {

            int points = calculatePoints(txn.getAmount());
            String month = txn.getTxnDate().getMonth().toString();

            monthlyRewards.putIfAbsent(month,
                    new MonthlyRewardSummary(0, 0.0));

            MonthlyRewardSummary summary = monthlyRewards.get(month);
            summary.setTotalPoints(summary.getTotalPoints() + points);
            summary.setTotalAmount(summary.getTotalAmount() + txn.getAmount());
        }

        List<TransactionDTO> transactionDTOList =
                transactions.stream()
                        .map(txn -> new TransactionDTO(
                                txn.getTxnId(),
                                txn.getAmount(),
                                txn.getTxnDate()))
                        .toList();

        return new RewardResponse(
                customer.getCustomerId(),
                customer.getName(),
                monthlyRewards,
                transactionDTOList
        );
    }

    private int calculatePoints(double amount) {

        if (amount <= 50) return 0;
        if (amount <= 100) return (int) (amount - 50);

        return (int) ((amount - 100) * 2 + 50);
    }
}