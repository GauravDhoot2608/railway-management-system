package com.workindia.app.railway_system.controller;

import com.workindia.app.railway_system.service.AuthService;
import com.workindia.app.railway_system.dto.BookingRequest;
import com.workindia.app.railway_system.dto.LoginRequest;
import com.workindia.app.railway_system.dto.TrainAvailableRequest;
import com.workindia.app.railway_system.dto.UserRequest;
import com.workindia.app.railway_system.entity.Booking;
import com.workindia.app.railway_system.entity.User;
import com.workindia.app.railway_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest){
        try{
            User newUser = userService.registerUser(userRequest);
            return ResponseEntity.ok(newUser);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        try{
            String token = authService.login(email, password);
            return ResponseEntity.ok(token);
        }
        catch(Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }


    @GetMapping("/trains/availability")
    public ResponseEntity<?> getSeatAvailability(@RequestParam("source") String source ,
                                                 @RequestParam("destination") String destination){

        List<TrainAvailableRequest> trainList = userService.getSeatAvailability(source,destination);
        if(trainList == null){
            return new ResponseEntity<>("No seat available between " + source + " and " + destination , HttpStatus.OK);
        }
        return ResponseEntity.ok(trainList);
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookSeat(@RequestBody BookingRequest bookingRequest ,
                                      @RequestHeader("token") String token){

        Long userId = bookingRequest.getUserId();
        if(!authService.validateAuthToken(userId,token)){
            return ResponseEntity.status(401).body("user not authorized");
        }

        try {
            Booking booking = userService.bookSeat(bookingRequest);
            return ResponseEntity.ok(booking);
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("{userId}/booking/{bookingId}")
    public ResponseEntity<?> getBookingDetails(@PathVariable Long userId,
                                               @PathVariable Long bookingId,
                                               @RequestHeader("token") String token){
        if(!authService.validateAuthToken(userId , token)){
            return ResponseEntity.status(401).body("user not authorized");
        }

        Booking booking = userService.getBookingDetails(userId , bookingId);

        return ResponseEntity.ok(booking);
    }

}
