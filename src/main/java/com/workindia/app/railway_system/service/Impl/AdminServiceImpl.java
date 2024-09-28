package com.workindia.app.railway_system.service.Impl;

import com.workindia.app.railway_system.dto.TrainDtoRequest;
import com.workindia.app.railway_system.entity.Train;
import com.workindia.app.railway_system.repository.TrainRepository;
import com.workindia.app.railway_system.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AdminServiceImpl implements AdminService {

    @Autowired
    private TrainRepository trainRepository;

    @Override
    public Train addTrain(TrainDtoRequest trainDtoRequest) {
        Train train = new Train();
        train.setTrainNumber(trainDtoRequest.getTrainNumber());
        train.setSource(trainDtoRequest.getSource());
        train.setDestination(trainDtoRequest.getDestination());
        train.setTotalSeats(trainDtoRequest.getTotalSeats());

        train = trainRepository.save(train);
        return train;
    }
}
