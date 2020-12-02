package com.wave.greenboxrest.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Getter
public class PositionCreateDto {

    @Min(value = 0, message = "Item id is never negative")
    public Long itemId;

    @Positive(message = "Amount should always be positive")
    public Double amount;

}
