package uz.sqbtransactionmanagement.model;

import lombok.Data;

import java.util.List;

@Data
public class GetStatementResult extends GenericResult {
    private List<TransactionStatement> statements;
}
