package com.workindia.app.railway_system.controller;

import com.workindia.app.railway_system.dto.TrainDtoRequest;
import com.workindia.app.railway_system.entity.Train;
import com.workindia.app.railway_system.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    private final String ADMIN_API_KEY = "admin2608";

    @PostMapping("/trains")
    public ResponseEntity<?> createTrain(@RequestBody TrainDtoRequest trainDtoRequest, @RequestHeader("x-api-key") String apiKey){
        if(!ADMIN_API_KEY.equals(apiKey)){
            return ResponseEntity.status(403).body("Invalid API Key");
        }
        try{
            Train newTrain = adminService.addTrain(trainDtoRequest);
            return ResponseEntity.ok(newTrain);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
