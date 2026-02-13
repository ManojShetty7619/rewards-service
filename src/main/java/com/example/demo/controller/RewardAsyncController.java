package com.example.demo.controller;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RewardResponse;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.service.RewardAsyncService;

@RestController
@RequestMapping("/api/rewards/async")
public class RewardAsyncController {

	private final RewardAsyncService asyncService;

	public RewardAsyncController(RewardAsyncService asyncService) {
		this.asyncService = asyncService;
	}

	@GetMapping("/{customerId}")
	public CompletableFuture<RewardResponse> getRewardsAsync(@PathVariable Long customerId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

		if (from.isAfter(to)) {
			throw new InvalidRequestException("From date cannot be after To date");
		}

		return asyncService.calculateRewardsAsync(customerId, from, to);
	}
}
