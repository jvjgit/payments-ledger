package com.jvj.paymentsledger.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "idempotency_keys")
public class IdempotencyKey {

    @Id
    @Column(name = "idempotency_key")
    private String key;

    @Column(name = "request_hash", nullable = false)
    private String requestHash;

    @Column(name = "response_status", nullable = false)
    private Integer responseStatus;

    @Column(name = "response_body", nullable = false, columnDefinition = "TEXT")
    private String responseBody;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected IdempotencyKey() {
        // required by JPA
    }

    public IdempotencyKey(String key, String requestHash, Integer responseStatus, String responseBody) {
        this.key = key;
        this.requestHash = requestHash;
        this.responseStatus = responseStatus;
        this.responseBody = responseBody;
        this.createdAt = Instant.now();
    }

    public String getKey() {
        return key;
    }

    public String getRequestHash() {
        return requestHash;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}