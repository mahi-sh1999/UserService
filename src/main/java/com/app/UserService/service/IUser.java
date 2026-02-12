package com.app.UserService.service;

import com.app.UserService.entity.User;

public interface IUser {

	
	
	User registerUser(User user);
	User getUserById(Long id);
}
