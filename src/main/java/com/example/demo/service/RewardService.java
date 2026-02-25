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
                                           LocalDate endDate) {

        log.info("Starting reward calculation for customerId={}, startDate={}, endDate={}",
                customerId, startDate, endDate);

        if (startDate.isAfter(endDate)) {
            log.error("Invalid date range: startDate={} is after endDate={}", startDate, endDate);
            throw new InvalidRequestException("From date cannot be after end date");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    log.error("Customer not found with id={}", customerId);
                    return new TransactionNotFoundException("Customer not found");
                });

        log.info("Customer found: {}", customer.getName());

        List<Transaction> transactions =
                transactionRepository.findByCustomerIdAndTxnDateBetween(
                        customerId, startDate, endDate);

        if (transactions.isEmpty()) {
            log.error("No transactions found for customerId={} between {} and {}",
                    customerId, startDate, endDate);
            throw new TransactionNotFoundException("No transactions found");
        }

        log.info("Total transactions fetched: {}", transactions.size());

        Map<String, MonthlyRewardSummary> monthlyRewards = new HashMap<>();

        for (Transaction txn : transactions) {

            int points = calculatePoints(txn.getAmount());
            String month = txn.getTxnDate().getMonth().toString();

            log.debug("Transaction id={}, amount={}, calculatedPoints={}",
                    txn.getTxnId(), txn.getAmount(), points);

            monthlyRewards.putIfAbsent(month,
                    new MonthlyRewardSummary(0, 0));

            MonthlyRewardSummary summary = monthlyRewards.get(month);

            summary.setTotalPoints(summary.getTotalPoints() + points);
            summary.setTotalAmount(summary.getTotalAmount() + txn.getAmount());
        }

        List<TransactionDTO> txnDTOs = transactions.stream()
                .map(t -> new TransactionDTO(
                        t.getTxnId(),
                        t.getAmount(),
                        t.getTxnDate()))
                .collect(Collectors.toList());

        log.info("Reward calculation completed successfully for customerId={}", customerId);

        return new RewardResponse(
                customer.getCustomerId(),
                customer.getName(),
                monthlyRewards,
                txnDTOs
        );
    }

    private int calculatePoints(double amount) {

        if (amount <= 50) {
            return 0;
        }

        if (amount <= 100) {
            return (int) (amount - 50);
        }

        return (int) ((amount - 100) * 2 + 50);
    }
}