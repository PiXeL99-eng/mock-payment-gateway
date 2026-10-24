package com.sayantan.mock_payment_gateway.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.sayantan.mock_payment_gateway.model.GatewayPaymentRequest;
import com.sayantan.mock_payment_gateway.model.GatewayPaymentResponse;
import com.sayantan.mock_payment_gateway.model.GatewayPaymentStatus;

class GatewayPaymentServiceTest {

    @Test
    void paymentIdDivisibleByThreeIsDeclined() {
        GatewayPaymentService service = new GatewayPaymentService(0);

        GatewayPaymentResponse response = service.processPayment(request(3L));

        assertEquals(GatewayPaymentStatus.DECLINED, response.status());
        assertEquals("Payment declined", response.message());
        assertValidGatewayTransactionId(response.gatewayTransactionId());
    }

    @Test
    void paymentIdWithRemainderOneIsApproved() {
        GatewayPaymentService service = new GatewayPaymentService(0);

        GatewayPaymentResponse response = service.processPayment(request(4L));

        assertEquals(GatewayPaymentStatus.APPROVED, response.status());
        assertEquals("Payment approved", response.message());
        assertValidGatewayTransactionId(response.gatewayTransactionId());
    }

    @Test
    void paymentIdWithRemainderTwoWaitsAndThenApproves() {
        long testDelayMillis = 25;
        GatewayPaymentService service = new GatewayPaymentService(testDelayMillis);

        long startedAt = System.nanoTime();
        GatewayPaymentResponse response = service.processPayment(request(5L));
        long elapsedMillis = (System.nanoTime() - startedAt) / 1_000_000;

        assertTrue(elapsedMillis >= testDelayMillis);
        assertEquals(GatewayPaymentStatus.APPROVED, response.status());
        assertEquals("Payment approved", response.message());
        assertValidGatewayTransactionId(response.gatewayTransactionId());
    }

    private GatewayPaymentRequest request(Long paymentId) {
        return new GatewayPaymentRequest(
            paymentId,
            "ORDER-1001",
            new BigDecimal("150.00"),
            "INR"
        );
    }

    private void assertValidGatewayTransactionId(String transactionId) {
        assertTrue(transactionId.startsWith("gw_"));
        assertDoesNotThrow(() -> UUID.fromString(transactionId.substring(3)));
    }
}

