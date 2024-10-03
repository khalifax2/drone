package com.thedrone.aero.enumerated;

import org.springframework.http.HttpStatus;

public enum State {
    IDLE,
    LOADING,
    LOADED,
    DELIVERING,
    DELIVERED,
    RETURNING
}
