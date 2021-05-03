package com.petribr.bookstoremanager.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petribr.bookstoremanager.user.dto.MessageDTO;
import com.petribr.bookstoremanager.user.dto.UserDTO;
import com.petribr.bookstoremanager.user.entity.User;
import com.petribr.bookstoremanager.user.exception.UserAlreadyExistsException;
import com.petribr.bookstoremanager.user.exception.UserNotFoundException;
import com.petribr.bookstoremanager.user.mapper.UserMapper;
import com.petribr.bookstoremanager.user.repository.UserRepository;
import com.petribr.bookstoremanager.user.utils.MessageDTOUtils;

@Service
public class UserService {

	private static final UserMapper userMapper = UserMapper.INSTANCE;

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public MessageDTO create(UserDTO userDTO) {
		verifyIfExists(userDTO.getUserName(), userDTO.getEmail());

		User userToCreate = userMapper.toModel(userDTO);
		User createdUser = userRepository.save(userToCreate);

		return MessageDTOUtils.creationMessage(createdUser);
	}

	public MessageDTO update(Long id, UserDTO userToUpdateDTO) {
		User foundUser = verifyAndGetuser(id);
		userToUpdateDTO.setId(id);
		
		User userToUpdate = userMapper.toModel(userToUpdateDTO);
		userToUpdate.setCreatedDate(foundUser.getCreatedDate());
		User updatedUser = userRepository.save(userToUpdate);
		
		return MessageDTOUtils.updateMessage(updatedUser);
	}	
	
	public UserDTO findById(Long id) {
		User foundUser = verifyAndGetuser(id);

		return userMapper.toDTO(foundUser);
	}

	private User verifyAndGetuser(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	public List<UserDTO> findAll() {

		return userRepository.findAll().stream().map(userMapper::toDTO).collect(Collectors.toList());
	}

	private void verifyIfExists(String username, String email) {
		userRepository.findByUserNameOrEmail(username, email).ifPresent(user -> {
			throw new UserAlreadyExistsException(username, email);
		});
	}

	public void delete(Long id) {
		verifyAndGetuser(id);
		userRepository.deleteById(id);
	}
}
