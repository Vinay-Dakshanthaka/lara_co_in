package com.lara.c2c.dto;

import com.lara.c2c.dto.data.SuccessStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class PhonePeParams extends SuccessStatus {

    public String response;
}
