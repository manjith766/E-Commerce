package com.manjith.entity;

import com.sun.jdi.event.StepEvent;
import lombok.Data;

@Data
public class BankDetails {

    private String accountNumber;
    private String accountHolderName;

    private String ifscCode;

}
