package com.thedrone.aero.enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Model {
    LIGHTWEIGHT("Lightweight", 100),
    MIDDLEWEIGHT("Middleweight", 200),
    CRUISERWEIGHT("Cruiserweight", 500),
    HEAVYWEIGHT("Heavyweight", 1000);

    private String name;
    private Integer maxCapacity;
}
