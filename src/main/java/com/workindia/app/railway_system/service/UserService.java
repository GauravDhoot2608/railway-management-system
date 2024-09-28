package com.workindia.app.railway_system.service;


import com.workindia.app.railway_system.dto.BookingRequest;
import com.workindia.app.railway_system.dto.TrainAvailableRequest;
import com.workindia.app.railway_system.dto.UserRequest;
import com.workindia.app.railway_system.entity.Booking;
import com.workindia.app.railway_system.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User registerUser(UserRequest userRequest);

    List<TrainAvailableRequest> getSeatAvailability(String source , String destination);

    Booking bookSeat(BookingRequest bookingRequest) throws Exception;

    Booking getBookingDetails(Long userId ,Long bookingId);

    User getUserByEmail(String email);
}
