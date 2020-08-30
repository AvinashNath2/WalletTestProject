package com.example.ewallet.models;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    ACTIVE, INACTIVE, REVERTED, PENDING
}
