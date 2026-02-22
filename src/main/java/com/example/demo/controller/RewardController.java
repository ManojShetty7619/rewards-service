package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RewardResponse;
import com.example.demo.service.RewardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
@Slf4j
public class RewardController {

	private final RewardService rewardService;

	@GetMapping("/{customerId}")
	public RewardResponse getRewards(@PathVariable Long customerId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

		log.info("Fetching rewards for customerId: {}, startDate: {}, endDate: {}", customerId, startDate, endDate);

		return rewardService.calculateRewards(customerId, startDate, endDate);
	}
}
