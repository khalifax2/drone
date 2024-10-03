package com.thedrone.aero.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thedrone.aero.enumerated.Model;
import com.thedrone.aero.enumerated.State;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


import java.util.List;

@Data
@Builder
public class DroneDto {

    @NotBlank
    @Size(max = 100, message = "Serial number maximum length is 100")
    private String serialNo;

    private Model model;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer batteryCapacity;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private State state;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<MedicationDto> medicationDtoList;

}
