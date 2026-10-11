package com.jvj.paymentsledger.dto;

import com.jvj.paymentsledger.entity.Transaction;
import com.jvj.paymentsledger.entity.TransactionStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransferResponse(
        UUID transactionId,
        UUID fromAccountId,
        UUID toAccountId,
        BigDecimal amount,
        TransactionStatus status,
        Instant createdAt
) {
    public static TransferResponse from(Transaction transaction) {
        return new TransferResponse(
                transaction.getId(),
                transaction.getFromAccount().getId(),
                transaction.getToAccount().getId(),
                transaction.getAmount(),
                transaction.getStatus(),
                transaction.getCreatedAt()
        );
    }
}
