package com.example.demo.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.dto.RewardResponse;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.service.RewardService;

@ExtendWith(MockitoExtension.class)
class RewardControllerTest {

    @Mock
    private RewardService rewardService;

    @InjectMocks
    private RewardController rewardController;

    private MockMvc mockMvc;

    private void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(rewardController)
                .setControllerAdvice(new com.example.demo.exception.GlobalExceptionHandler())
                .build();
    }

    @Test
    void shouldReturnRewardsSuccessfully() throws Exception {
        setup();

        RewardResponse response = new RewardResponse(
                1L,
                "John",
                new HashMap<>(),
                null
        );

        when(rewardService.calculateRewards(anyLong(), any(), any(), any()))
                .thenReturn(response);

        mockMvc.perform(get("/api/rewards")
                        .param("customerId", "1")
                        .param("months", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.customerName").value("John"));
    }

    @Test
    void shouldReturnBadRequestWhenMonthsAndDateRangeProvided() throws Exception {
        setup();

        when(rewardService.calculateRewards(anyLong(), any(), any(), any()))
                .thenThrow(new InvalidRequestException(
                        "Provide either months or startDate & endDate, not both"));

        mockMvc.perform(get("/api/rewards")
                        .param("customerId", "1")
                        .param("months", "3")
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-03-31"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Provide either months or startDate & endDate, not both"));
    }

    @Test
    void shouldReturnBadRequestWhenStartDateAfterEndDate() throws Exception {
        setup();

        when(rewardService.calculateRewards(anyLong(), any(), any(), any()))
                .thenThrow(new InvalidRequestException(
                        "From date cannot be after end date"));

        mockMvc.perform(get("/api/rewards")
                        .param("customerId", "1")
                        .param("startDate", "2025-05-01")
                        .param("endDate", "2025-03-01"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("From date cannot be after end date"));
    }

    @Test
    void shouldReturnNotFoundWhenNoTransactions() throws Exception {
        setup();

        when(rewardService.calculateRewards(anyLong(), any(), any(), any()))
                .thenThrow(new com.example.demo.exception.TransactionNotFoundException(
                        "No transactions found"));

        mockMvc.perform(get("/api/rewards")
                        .param("customerId", "1")
                        .param("months", "3"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("No transactions found"));
    }
}