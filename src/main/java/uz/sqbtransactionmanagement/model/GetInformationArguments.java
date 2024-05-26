package uz.sqbtransactionmanagement.model;

import lombok.Data;

import java.util.List;

@Data
public class GetInformationArguments extends GenericArguments {
    private List<GenericParam> parameters;
    private long serviceId;
}
