package com.example.demo.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardResponse {

    private Long customerId;
    private String customerName;
    private Map<String, MonthlyRewardSummary> monthlyRewards;
    private List<TransactionDTO> transactions;
}
