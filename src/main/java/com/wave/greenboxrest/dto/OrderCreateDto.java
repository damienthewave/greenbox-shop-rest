package com.wave.greenboxrest.dto;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

public class OrderCreateDto {

    @NotBlank
    public String personName;

    @Pattern(regexp = "((\\+|00)\\d{2})?(\\d{1,9})", message = "Incorrect phone number")
    public String phoneNumber;

    @Pattern(regexp = "([A-Za-z]{2,}( [A-Za-z]{2,})?),? ([a-zA-Z]{3,}( [A-Za-z]{2,})?) \\d+",
            message = "Incorrect address")
    public String address;

    public String orderComment;

    @NotEmpty(message = "Position list cannot be empty")
    @Getter
    public List<@Valid PositionCreateDto> positions;

}   
