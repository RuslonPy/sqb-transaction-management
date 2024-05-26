package uz.sqbtransactionmanagement.model;

import lombok.Data;

@Data
public class RequestParamDescriptor {
    private long srv_id;
    private String name;
    private String type;
    private String validator;
    private int length;
    private String caption_ru;
    private String caption_uz;
    private boolean required;
    private boolean visible;
    private int order;
}
