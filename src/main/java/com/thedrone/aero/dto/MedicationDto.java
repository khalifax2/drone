package com.thedrone.aero.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicationDto {

    @NotBlank(message = "Name must not be blank")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Name must contain only letters, numbers, hyphens, or underscores")
    private String name;

    @NotNull(message = "Weight must not be null")
    @Digits(integer = 6, fraction = 0, message = "Name must contain only numbers upto 999999")
    private Integer weight;

    @NotBlank(message = "Code must not be blank")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Code must contain only uppercase letters, underscores, or numbers")
    private String code;

    @NotBlank(message = "Image must not be blank")
    private String imagePath;

}
