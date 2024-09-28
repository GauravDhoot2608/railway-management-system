package com.workindia.app.railway_system.service.Impl;

import com.workindia.app.railway_system.Exception.ResourceNotFoundException;
import com.workindia.app.railway_system.dto.BookingRequest;
import com.workindia.app.railway_system.dto.TrainAvailableRequest;
import com.workindia.app.railway_system.dto.UserRequest;
import com.workindia.app.railway_system.entity.Booking;
import com.workindia.app.railway_system.entity.Train;
import com.workindia.app.railway_system.entity.User;
import com.workindia.app.railway_system.repository.BookingRepository;
import com.workindia.app.railway_system.repository.TrainRepository;
import com.workindia.app.railway_system.repository.UserRepository;
import com.workindia.app.railway_system.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public User registerUser(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        user = userRepository.save(user);
        return user;
    }

    @Override
    public List<TrainAvailableRequest> getSeatAvailability(String source, String destination) {
        List<Train> trains = trainRepository.findBySourceAndDestination(source,destination);

        List<TrainAvailableRequest> trainAvailableRequests = new ArrayList<>();
        for(Train train : trains){
            int availableSeats = train.getTotalSeats() - findAvailableSeatsByTrain(train.getId());

            TrainAvailableRequest trainAvailableRequest = new TrainAvailableRequest();
            trainAvailableRequest.setTrainNumber(train.getTrainNumber());
            trainAvailableRequest.setSource(train.getSource());
            trainAvailableRequest.setDestination(train.getDestination());
            trainAvailableRequest.setAvailableSeats(availableSeats);

            trainAvailableRequests.add(trainAvailableRequest);
        }

        return trainAvailableRequests;
    }

    private int findAvailableSeatsByTrain(Long trainId){

        int count = bookingRepository.findByTrainId(trainId).get().size();
        return count;
    }

    @Transactional
    @Override
    public Booking bookSeat(BookingRequest bookingRequest) throws Exception{

        User user = userRepository.findById(bookingRequest.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User","userId",bookingRequest.getUserId())
        );

        Train train = trainRepository.findById(bookingRequest.getTrainId()).orElseThrow(
                () -> new ResourceNotFoundException("Train","trainId",bookingRequest.getTrainId())
        );

        int availableSeats = train.getTotalSeats() - findAvailableSeatsByTrain(train.getId());

        if(availableSeats > 0){
            Booking booking = new Booking();
            booking.setBookedDate(new Date());
            booking.setUserId(bookingRequest.getUserId());
            booking.setTrainId(bookingRequest.getTrainId());

            booking = bookingRepository.save(booking);
            return booking;
        }

        throw new Exception("No Seat Available");
    }

    @Override
    public Booking getBookingDetails(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new ResourceNotFoundException("Booking","bookingId",bookingId)
        );
        return booking;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

}
