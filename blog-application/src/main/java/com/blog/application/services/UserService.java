package com.blog.application.services;

import java.util.List;

import com.blog.application.payloads.UserDto;

import jakarta.validation.Valid;

public interface UserService {
	
	UserDto registerNewUser(@Valid UserDto userDto);
	
	UserDto createUser(UserDto user);
	
	UserDto updataUser(UserDto user,Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void  deleteUser(Integer UserId);

	
	

}
