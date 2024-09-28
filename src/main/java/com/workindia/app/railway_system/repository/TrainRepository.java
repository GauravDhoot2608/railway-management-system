package com.workindia.app.railway_system.repository;

import com.workindia.app.railway_system.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train,Long> {

    List<Train> findBySourceAndDestination(String source, String destination);
}
