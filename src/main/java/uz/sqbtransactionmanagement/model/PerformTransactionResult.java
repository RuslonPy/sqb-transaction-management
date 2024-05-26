package uz.sqbtransactionmanagement.model;


import lombok.Data;

import java.util.List;

@Data
public class PerformTransactionResult extends GenericResult {
    private List<GenericParam> parameters;
    private long providerTrnId;
}
