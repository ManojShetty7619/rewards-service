package com.example.demo.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.dto.RewardResponse;
import com.example.demo.exception.GlobalExceptionHandler;
import com.example.demo.service.RewardService;

@ExtendWith(MockitoExtension.class)
class RewardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RewardService rewardService;

    @InjectMocks
    private RewardController rewardController;

    @BeforeEach
    void setup() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(rewardController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setConversionService(new DefaultFormattingConversionService())
                .build();
    }

    @Test
    void shouldReturnRewardsSuccessfully() throws Exception {

        RewardResponse response =
                new RewardResponse(
                        1L,
                        "John",
                        new HashMap<>(),
                        null
                );

        when(rewardService.calculateRewards(
                eq(1L),
                any(),
                any(),
                any()))
                .thenReturn(response);

        mockMvc.perform(get("/api/rewards")
                        .param("customerId", "1")
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-03-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.customerName").value("John"));
    }

    @Test
    void shouldReturnDefaultWhenNoDateProvided() throws Exception {

        RewardResponse response =
                new RewardResponse(
                        1L,
                        "John",
                        new HashMap<>(),
                        null
                );

        when(rewardService.calculateRewards(
                eq(1L),
                isNull(),
                isNull(),
                isNull()))
                .thenReturn(response);

        mockMvc.perform(get("/api/rewards")
                        .param("customerId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequestWhenMonthsAndDateRangeProvided() throws Exception {

        mockMvc.perform(get("/api/rewards")
                        .param("customerId", "1")
                        .param("startDate", "2025-01-01")
                        .param("endDate", "2025-03-31")
                        .param("months", "2"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenCustomerIdMissing() throws Exception {

        mockMvc.perform(get("/api/rewards"))
                .andExpect(status().isBadRequest());
    }
}
