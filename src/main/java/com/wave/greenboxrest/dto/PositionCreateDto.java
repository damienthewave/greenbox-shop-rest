package com.wave.greenboxrest.dto;

import javax.validation.constraints.Positive;

public class PositionCreateDto {

    public Long itemId;

    @Positive
    public Double amount;

}
