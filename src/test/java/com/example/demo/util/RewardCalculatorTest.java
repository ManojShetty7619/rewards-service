package com.example.demo.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RewardCalculatorTest {

	private RewardCalculator calculator;

	@BeforeEach
	void setup() {
		calculator = new RewardCalculator();
	}

	@Test
	void shouldReturnZeroWhenBelow50() {
		assertEquals(0, calculator.calculate(40));
	}

	@Test
	void shouldReturnPointsBetween50And100() {
		assertEquals(30, calculator.calculate(80));
	}

	@Test
	void shouldReturnPointsAbove100() {
		assertEquals(90, calculator.calculate(120));
	}

	@Test
	void shouldReturnExact50PointsFor100() {
		assertEquals(50, calculator.calculate(100));
	}
}
