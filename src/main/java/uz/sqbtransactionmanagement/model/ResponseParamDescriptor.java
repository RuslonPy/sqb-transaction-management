package uz.sqbtransactionmanagement.model;

import lombok.Data;

@Data
public class ResponseParamDescriptor {
    private long srv_id;
    private String name;
    private String type;
    private String caption_ru;
    private String caption_uz;
}