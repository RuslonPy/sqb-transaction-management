package uz.sqbtransactionmanagement.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GenericResult {
    private String errorMsg;
    private int status;
    private LocalDateTime timeStamp;
}
