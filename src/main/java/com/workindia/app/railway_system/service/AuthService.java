package com.workindia.app.railway_system.service;


import com.workindia.app.railway_system.Exception.AuthenticationException;
import com.workindia.app.railway_system.entity.AuthEntity;
import com.workindia.app.railway_system.repository.AuthRepository;
import com.workindia.app.railway_system.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private UserService userService;

	public AuthEntity getAuthEntityByAccessToken(String accessToken) {
		return authRepository.findByAccessToken(accessToken).orElse(null);
	}

	public boolean validateAuthToken(Long userId, String token) {
		Optional<AuthEntity> authEntityOptional = authRepository.findByUserIdAndAccessToken(userId, token);
		return authEntityOptional.isPresent() && authEntityOptional.get().getExpiresIn() > System.currentTimeMillis();
	}

	public String login(String email, String password){
		User user = userService.getUserByEmail(email);
		if (user == null){
			throw new AuthenticationException("Email is not registered");
		}
		try {
			boolean valid = password.matches(user.getPassword());
			if (valid) {
				AuthEntity authEntity = createAuthEntityForUser(user);
				return authEntity.getAccessToken();
			} else {
				throw new AuthenticationException("Password does not match");
			}
		} catch (Exception e) {
			throw new AuthenticationException("Password does not match");
		}
	}

	private AuthEntity createAuthEntityForUser(User user) {
		AuthEntity authEntity = new AuthEntity();
		authEntity.setUserId(user.getId());
		authEntity.setAccessToken(UUID.randomUUID().toString());
		authEntity.setExpiresIn(System.currentTimeMillis() + 10*60*1000);
		authEntity.setCreatedAt(System.currentTimeMillis());
		return authRepository.save(authEntity);
	}
}
