package uz.sqbtransactionmanagement.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionStatement {
    private long amount;
    private long providerTrnId;
    private long transactionId;
    private LocalDateTime transactionTime;
}
