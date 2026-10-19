package com.sayantan.mock_payment_gateway.model;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record GatewayPaymentRequest(

    @Positive(message = "Payment ID must be greater than zero")
    @NotNull(message = "Payment ID is required")
    Long paymentId,
    
    @NotBlank(message = "Order ID is required")
    String orderId,

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    BigDecimal amount,

    @NotBlank(message = "Currency is required")
    String currency
) {
    
}
