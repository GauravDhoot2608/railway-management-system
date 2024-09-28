package com.workindia.app.railway_system.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BookingRequest {

    private Long userId;

    private Long trainId;
}
