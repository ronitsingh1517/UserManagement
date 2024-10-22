package com.apica.assignment.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.apica.assignment.dao.UserRepository;
import com.apica.assignment.dto.UserDTO;
import com.apica.assignment.entity.User;
import com.apica.assignment.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    
    private final UserRepository userRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;
    
    public UserServiceImpl(UserRepository userRepository,KafkaTemplate<String, String> kafkaTemplate) {
    	this.userRepository = userRepository;
    	this.kafkaTemplate = kafkaTemplate;
    }

    private static final String USER_EVENTS_TOPIC = "user-events";

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());

        User savedUser = userRepository.save(user);

        kafkaTemplate.send(USER_EVENTS_TOPIC, "User registered: " + savedUser.getUsername());

        return new UserDTO(savedUser.getUsername(), savedUser.getEmail(), savedUser.getRole());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setRole(userDTO.getRole());
            userRepository.save(user);

            kafkaTemplate.send(USER_EVENTS_TOPIC, "User updated: " + user.getUsername());
            return new UserDTO(user.getUsername(), user.getEmail(), user.getRole());
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        kafkaTemplate.send(USER_EVENTS_TOPIC, "User deleted: " + id);
    }

    @Override
    public UserDTO getUser(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDTO(user.getUsername(), user.getEmail(), user.getRole()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getUsername(), user.getEmail(), user.getRole()))
                .collect(Collectors.toList());
    }
}
