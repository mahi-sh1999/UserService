package com.app.UserService.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.UserService.dto.ChannelType;
import com.app.UserService.dto.NotificationRequest;
import com.app.UserService.dto.NotificationType;
import com.app.UserService.entity.User;
import com.app.UserService.repository.UserRepository;
import com.app.UserService.service.exception.UserNotFoundException;

@Service
public class UserServiceImpl implements IUser {
	
	private static final Logger logger=
			LoggerFactory.getLogger(UserServiceImpl.class);
	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RestTemplate restTemplate;
	   @Autowired
	    private PasswordEncoder passwordEncoder;

	
	@Override
	public User registerUser(User user) {

	    // ✅ Encode password BEFORE saving
	    user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

	    User savedUser = userRepository.save(user);

	    NotificationRequest request = new NotificationRequest();
	    request.setNotificationType(NotificationType.USER_REGISTERED);
	    request.setChannelType(ChannelType.EMAIL);
	    request.setUserId(savedUser.getUserId().toString());
	    request.setEmail(savedUser.getUserEmail());

	    try {
	        restTemplate.postForObject(
	            "http://NOTIFICATIONSERVICE/notifications/send",
	            request,
	            String.class
	        );
	    } catch (Exception e) {
	        logger.error("Failed to send notification for user {}", savedUser.getUserId());
	    }

	    return savedUser;
	}
	
	
	
	
	
	public User getUserById(Long id) {
		
		logger.info("Fetching user with id: {} ", id);

	    return userRepository.findById(id)
	            .orElseThrow(() ->{
	            	logger.error("User not found with id: {}",id);
	            return  new  UserNotFoundException("User not found  with id:"+ id);
	            });
	}
	
	
	
	public void deleteUser(Long id) {
		
		logger.info("User has deleted with id:{}",id);
	userRepository.deleteById(id);
	}
	
	
	//update the existing user
	
	public User updateUser(User newUser) {
		
		User existUSer = userRepository.findById(newUser.getUserId()).orElseThrow( ()-> new RuntimeException("User Not Found")); 
		
		
		
		if(newUser.getUserName()!=null) {
			existUSer.setUserName(newUser.getUserName());
		}
		if(newUser.getUserEmail()!=null) {
			existUSer.setUserEmail(newUser.getUserEmail());
		}
		if(newUser.getUserPassword()!=null) {
			existUSer.setUserPassword(newUser.getUserPassword());
		}
		if(newUser.getUserPhone()!=null)
		{
			existUSer.setUserPhone(newUser.getUserPhone());
		}
		
		
		
		return userRepository.save(existUSer);
	}
	
	
	public List<User> findAllEmp(){
		
		return userRepository.findAll();
	}

	
	
	
	

}
