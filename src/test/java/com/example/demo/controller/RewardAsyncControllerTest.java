package com.example.demo.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import com.example.demo.dto.RewardResponse;
import com.example.demo.service.RewardAsyncService;

class RewardAsyncControllerTest {

    @Test
    void shouldReturnAsyncResponse() throws Exception {

        RewardAsyncService service = mock(RewardAsyncService.class);

        RewardResponse mockResponse =
                new RewardResponse(1L, Map.of(), 100, List.of());

        when(service.calculateRewardsAsync(anyLong(), any(), any()))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));

        RewardAsyncController controller =
                new RewardAsyncController(service);

        var future = controller.getRewardsAsync(
                1L,
                LocalDate.of(2025,1,1),
                LocalDate.of(2025,3,31));

        assertEquals(100, future.get().getTotalRewards());
    }
}

