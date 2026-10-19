package com.sayantan.mock_payment_gateway.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sayantan.mock_payment_gateway.model.GatewayPaymentRequest;
import com.sayantan.mock_payment_gateway.model.GatewayPaymentResponse;
import com.sayantan.mock_payment_gateway.model.GatewayPaymentStatus;

@Service 
public class GatewayPaymentService {
    public GatewayPaymentResponse processPayment(GatewayPaymentRequest request) {
        long outcome = request.paymentId() % 3;

        if (outcome == 0) {
            return toResponse(GatewayPaymentStatus.DECLINED, "Payment declined");
        }

        if (outcome == 2) {
            sleepForTimeoutScenario();
        }

        return toResponse(GatewayPaymentStatus.APPROVED, "Payment approved");
    }

    private GatewayPaymentResponse toResponse(GatewayPaymentStatus status, String message) {
        return new GatewayPaymentResponse(
                "gw_" + UUID.randomUUID(),
                status,
                message
        );
    }

    private void sleepForTimeoutScenario() {
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
