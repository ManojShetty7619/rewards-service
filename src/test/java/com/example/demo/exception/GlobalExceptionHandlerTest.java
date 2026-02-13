package com.example.demo.exception;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    @Test
    void shouldCreateErrorResponse() {

        TransactionNotFoundException ex =
                new TransactionNotFoundException("Not found");

        assertEquals("Not found", ex.getMessage());
    }
}
