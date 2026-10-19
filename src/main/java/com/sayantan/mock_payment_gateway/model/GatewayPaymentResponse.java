package com.sayantan.mock_payment_gateway.model;

public record GatewayPaymentResponse(
    String gatewayTransactionId,
    GatewayPaymentStatus status,
    String message
) {
}
