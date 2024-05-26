package uz.sqbtransactionmanagement.model;

import lombok.Data;

import java.util.List;

@Data
public class GetInformationResult extends GenericResult {
    private List<GenericParam> parameters;
}
