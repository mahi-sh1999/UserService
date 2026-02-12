package com.app.UserService.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	

	
	@Override
	public User registerUser(User user) {
		// TODO Auto-generated method stub
		
		//save to the db
		User savedUser= userRepository.save(user);
		
		
		//prepare the notification
		NotificationRequest request = new NotificationRequest();
		request.setNotificationType(NotificationType.USER_REGISTERED);
		request.setChannelType(ChannelType.EMAIL);
		request.setUserId(savedUser.getUser_id().toString());
		request.setEmail(savedUser.getEmail());
		
		
		//call to the notification service
		
		try {
		restTemplate.postForObject("http://NOTIFICATIONSERVICE/notifications/send", request, String.class);
		
		}
		catch (Exception e) {
	        // Notification failure should NOT break user creation
			logger.error("Failed to send notification for user {}", savedUser.getUser_id());
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
		
		User existUSer = userRepository.findById(newUser.getUser_id()).orElseThrow( ()-> new RuntimeException("User Not Found")); 
		
		
		
		if(newUser.getName()!=null) {
			existUSer.setName(newUser.getName());
		}
		if(newUser.getEmail()!=null) {
			existUSer.setEmail(newUser.getEmail());
		}
		if(newUser.getPassword()!=null) {
			existUSer.setPassword(newUser.getPassword());
		}
		if(newUser.getPhone()!=null)
		{
			existUSer.setPhone(newUser.getPhone());
		}
		
		
		
		return userRepository.save(existUSer);
	}
	
	
	public List<User> findAllEmp(){
		
		return userRepository.findAll();
	}

	
	
	
	

}
