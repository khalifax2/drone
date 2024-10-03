package com.thedrone.aero.service;

import com.thedrone.aero.dto.LoadingMedDto;

public interface LoadMedicationService {
    void loadMedication(LoadingMedDto loadingMedDto) throws InterruptedException;
}
