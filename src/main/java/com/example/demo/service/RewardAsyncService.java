package com.example.demo.service;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import com.example.demo.dto.RewardResponse;

public interface RewardAsyncService {

	CompletableFuture<RewardResponse> calculateRewardsAsync(Long customerId, LocalDate from, LocalDate to);
}
