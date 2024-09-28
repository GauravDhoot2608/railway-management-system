package com.workindia.app.railway_system.dto;

import lombok.Data;

@Data
public class TrainAvailableRequest {

    private String trainNumber;
    private String source;
    private String destination;
    private Integer availableSeats;
}
