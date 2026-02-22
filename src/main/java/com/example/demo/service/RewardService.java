package com.example.demo.service;

import java.time.LocalDate;

import com.example.demo.dto.RewardResponse;

public interface RewardService {

    RewardResponse calculateRewards(Long customerId,
                                    LocalDate startDate,
                                    LocalDate endDate);
}