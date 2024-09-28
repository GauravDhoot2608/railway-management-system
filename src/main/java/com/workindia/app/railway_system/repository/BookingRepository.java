package com.workindia.app.railway_system.repository;

import com.workindia.app.railway_system.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    Optional<List<Booking>> findByTrainId(Long trainId);

}
