# Mock Payment Gateway

Stateless Spring Boot service that simulates an external payment processor for the Payment Platform project. It has no database.

## Current behavior

- Runs on `http://localhost:8081`.
- Exposes `POST /gateway/payments`.
- Creates gateway transaction IDs in the form `gw_<uuid>`.
- Uses `paymentId % 3` for deterministic results:
  - remainder `0`: `DECLINED`
  - remainder `1`: `APPROVED`
  - remainder `2`: waits about 10 seconds, then returns `APPROVED`

The delay is intentional so the Payment Platform's shorter HTTP timeout can be tested.
Its duration is configured with `gateway.timeout-delay-ms`, which defaults to `10000` milliseconds.

## Prerequisites

- Java 21

## Run locally

From this directory:

```powershell
.\mvnw.cmd spring-boot:run
```

Verify the service:

```powershell
Invoke-RestMethod http://localhost:8081/hello
```

Test the payment endpoint directly:

```powershell
$body = @{
    paymentId = 4
    orderId = "ORDER-1001"
    amount = 150.00
    currency = "INR"
} | ConvertTo-Json

Invoke-RestMethod -Method Post `
    -Uri http://localhost:8081/gateway/payments `
    -ContentType "application/json" `
    -Body $body
```

Change `paymentId` to `3` to test a decline or `5` to test the delayed response. Every three consecutive payment IDs cover all outcomes.

Stop the application with `Ctrl+C`.

## Run the complete system

Start the components in this order:

1. PostgreSQL with `docker compose up -d` from `payment-platform`.
2. This mock gateway.
3. The Payment Platform, after setting its required UTC JVM timezone.

See `../payment-platform/README.md` for complete commands and database inspection instructions.

