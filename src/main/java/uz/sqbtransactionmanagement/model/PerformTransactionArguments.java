package uz.sqbtransactionmanagement.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PerformTransactionArguments extends GenericArguments {
    private long amount;
    private List<GenericParam> parameters;
    private long serviceId;
    private long transactionId;
    private LocalDateTime transactionTime;
}
