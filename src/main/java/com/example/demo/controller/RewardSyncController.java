package com.example.demo.controller;


import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RewardResponse;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.service.RewardService;

@RestController
@RequestMapping("/api/rewards/sync")
public class RewardSyncController {

    private final RewardService rewardService;

    public RewardSyncController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping("/{customerId}")
    public RewardResponse getRewards(
            @PathVariable Long customerId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate from,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate to) {

        if (from.isAfter(to)) {
            throw new InvalidRequestException(
                    "From date cannot be after To date");
        }

        return rewardService.calculateRewards(customerId, from, to);
    }
}
