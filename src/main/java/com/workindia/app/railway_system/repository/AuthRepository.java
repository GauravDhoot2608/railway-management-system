package com.workindia.app.railway_system.repository;

import com.workindia.app.railway_system.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<AuthEntity, Long> {

	Optional<AuthEntity> findByAccessToken(String accessToken);

	Optional<AuthEntity> findByUserIdAndAccessToken(Long userId, String token);
}
