package com.workindia.app.railway_system.service;

import com.workindia.app.railway_system.dto.TrainDtoRequest;
import com.workindia.app.railway_system.entity.Train;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    Train addTrain(TrainDtoRequest trainDtoRequest);
}
