package com.thedrone.aero.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoadingMedDto {
    @NotBlank
    private String droneId;
    private List<String> medicationIds;
}
