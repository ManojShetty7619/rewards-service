package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.RewardResponse;
import com.example.demo.service.RewardService;

@ExtendWith(MockitoExtension.class)
class RewardControllerTest {

	@Mock
	private RewardService rewardService;

	@InjectMocks
	private RewardController rewardController;

	@Test
	void getRewards_success() {

		when(rewardService.calculateRewards(anyLong(), any(), any())).thenReturn(new RewardResponse());

		RewardResponse response = rewardController.getRewards(1L, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 2, 1));

		assertNotNull(response);
		verify(rewardService, times(1)).calculateRewards(anyLong(), any(), any());
	}
}
