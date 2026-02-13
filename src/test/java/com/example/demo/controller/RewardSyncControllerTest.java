package com.example.demo.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.example.demo.dto.RewardResponse;
import com.example.demo.service.RewardService;

class RewardSyncControllerTest {

	@Test
	void shouldReturnResponse() {

		RewardService service = mock(RewardService.class);

		RewardResponse mockResponse = new RewardResponse(1L, Map.of(), 100, List.of());

		when(service.calculateRewards(anyLong(), any(), any())).thenReturn(mockResponse);

		RewardSyncController controller = new RewardSyncController(service);

		RewardResponse response = controller.getRewards(1L, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 3, 31));

		assertEquals(100, response.getTotalRewards());
	}
}
