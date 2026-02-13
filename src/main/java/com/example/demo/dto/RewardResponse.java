package com.example.demo.dto;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class RewardResponse {

	private Long customerId;
	private Map<YearMonth, Integer> monthlyRewards;
	private int totalRewards;
	private List<?> transactions;

	public RewardResponse() {
	}

	public RewardResponse(Long customerId, Map<YearMonth, Integer> monthlyRewards, int totalRewards,
			List<?> transactions) {
		this.customerId = customerId;
		this.monthlyRewards = monthlyRewards;
		this.totalRewards = totalRewards;
		this.transactions = transactions;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Map<YearMonth, Integer> getMonthlyRewards() {
		return monthlyRewards;
	}

	public void setMonthlyRewards(Map<YearMonth, Integer> monthlyRewards) {
		this.monthlyRewards = monthlyRewards;
	}

	public int getTotalRewards() {
		return totalRewards;
	}

	public void setTotalRewards(int totalRewards) {
		this.totalRewards = totalRewards;
	}

	public List<?> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<?> transactions) {
		this.transactions = transactions;
	}
}
