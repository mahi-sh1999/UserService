package com.app.UserService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.UserService.entity.User;
import com.app.UserService.service.UserServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserServiceImpl service;

	@GetMapping("/hello")
	public String helloMessage() {
		return "Mahi from  userService";
	}

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {

		return ResponseEntity.ok(service.registerUser(user));
	}

	@GetMapping("/{id}")
	public User fetchUser(@PathVariable Long id) {

		return service.getUserById(id);

	}
	
	@GetMapping("/emps")
	public List<User> fetchAllEmps(){
		return service.findAllEmp();
	}

	@DeleteMapping("/{id}")
	public void delteUser(@PathVariable Long id) {

		service.deleteUser(id);

	}

	@PutMapping("/update")
	public User updateTheUser(@RequestBody User user) {
		return service.updateUser(user);
	}

}
