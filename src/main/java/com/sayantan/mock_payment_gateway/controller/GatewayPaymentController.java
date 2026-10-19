package com.sayantan.mock_payment_gateway.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sayantan.mock_payment_gateway.model.GatewayPaymentRequest;
import com.sayantan.mock_payment_gateway.model.GatewayPaymentResponse;
import com.sayantan.mock_payment_gateway.service.GatewayPaymentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController 
public class GatewayPaymentController {

    private final GatewayPaymentService gatewayPaymentService;

    public GatewayPaymentController(GatewayPaymentService gatewayPaymentService) {
        this.gatewayPaymentService = gatewayPaymentService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Payment Gateway working";
    }

    @PostMapping("/gateway/payments")
    public GatewayPaymentResponse processPayment(
        @Valid @RequestBody GatewayPaymentRequest request
    ) { 
        return gatewayPaymentService.processPayment(request);
    }
    
}
