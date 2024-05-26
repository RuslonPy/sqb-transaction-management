package uz.sqbtransactionmanagement.model;

import lombok.Data;

import java.util.List;

@Data
public class RequestParamDescriptorList {
    private int count;
    private List<RequestParamDescriptor> parameters;
}
