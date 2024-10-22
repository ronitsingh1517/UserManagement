package com.apica.assignment.service;

import java.util.List;

import com.apica.assignment.dto.UserDTO;

public interface UserService {
	UserDTO registerUser(UserDTO userDTO);

	UserDTO updateUser(Long id, UserDTO userDTO);

	void deleteUser(Long id);

	UserDTO getUser(Long id);

	List<UserDTO> getAllUsers();
}
