package com.petribr.bookstoremanager.user.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.petribr.bookstoremanager.user.dto.UserDTO;
import com.petribr.bookstoremanager.user.entity.User;
import com.petribr.bookstoremanager.user.exception.UserNotFoundException;
import com.petribr.bookstoremanager.user.builder.UserDTOBuilder;
import com.petribr.bookstoremanager.user.dto.MessageDTO;
import com.petribr.bookstoremanager.user.dto.UserDTO;
import com.petribr.bookstoremanager.user.entity.User;
import com.petribr.bookstoremanager.user.exception.UserAlreadyExistsException;
import com.petribr.bookstoremanager.user.mapper.UserMapper;
import com.petribr.bookstoremanager.user.repository.UserRepository;
import com.petribr.bookstoremanager.user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	private final UserMapper userMapper = UserMapper.INSTANCE;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	private UserDTOBuilder userDTOBuilder;

	@BeforeEach
	void setup() {
		userDTOBuilder = UserDTOBuilder.builder().build();
	}

	@Test
	public void whenNewUserIsInformedThenItShouldBeCreated() {
		UserDTO expectedCreatedUserDTO = userDTOBuilder.buildUserDTO();
		User expectedCreatedUser = userMapper.toModel(expectedCreatedUserDTO);
		String expectedCreationMessage = String.format("User %s with ID %s sucessfully created",
				expectedCreatedUserDTO.getUserName(), expectedCreatedUserDTO.getId());

		when(userRepository.findByUserNameOrEmail(expectedCreatedUserDTO.getUserName(),
				expectedCreatedUserDTO.getEmail())).thenReturn(Optional.empty());
		when(userRepository.save(any(User.class))).thenReturn(expectedCreatedUser);

		MessageDTO creationMessage = userService.create(expectedCreatedUserDTO);

		assertThat(expectedCreationMessage, is(equalTo(creationMessage.getMessage())));
	}

	@Test
	public void whenExistingUserIsInformedThenAnExceptionShouldBeThrown() {
		UserDTO expectedDuplicatedUserDTO = userDTOBuilder.buildUserDTO();
		User expectedDuplicatedUser = userMapper.toModel(expectedDuplicatedUserDTO);

		when(userRepository.findByUserNameOrEmail(expectedDuplicatedUserDTO.getUserName(),
				expectedDuplicatedUserDTO.getEmail())).thenReturn(Optional.of(expectedDuplicatedUser));

		assertThrows(UserAlreadyExistsException.class, () -> userService.create(expectedDuplicatedUserDTO));
	}
	
	@Test
    @DisplayName(" ")
    void whenValidUserIdIsGivenThenItShouldBeDeleted() {	
    	// given
    	UserDTO expectedDeletedUserDTO = userDTOBuilder.buildUserDTO();
    	User expectedDeletedUser = userMapper.toModel(expectedDeletedUserDTO);
    	    	
    	// when
    	Long expectedDeletedUserId = expectedDeletedUserDTO.getId();
    	doNothing().when(userRepository).deleteById(expectedDeletedUserId);
    	when(userRepository.findById(expectedDeletedUserId)).thenReturn(Optional.of(expectedDeletedUser));
    	
    	userService.delete(expectedDeletedUserId);
    	// Then
    	verify(userRepository, times(1)).deleteById(expectedDeletedUserId);
    	verify(userRepository, times(1)).findById(expectedDeletedUserId);
    }
 
    @Test
    @DisplayName(" ")
    void whenInvalidUserIdIsGivenThenAnExceptionShouldBeThrown() {	
    	// given
    	var expectedInvalidUserId = 2L;
    	
    	// when    	
    	when(userRepository.findById(expectedInvalidUserId)).thenReturn(Optional.empty());
    	
    	// Then
    	assertThrows(UserNotFoundException.class, () -> userService.delete(expectedInvalidUserId));
    }
    
	@Test
	public void whenExistingUserIsInformedThenItShouldBeUpdated() {
		UserDTO expectedUpdatedUserDTO = userDTOBuilder.buildUserDTO();
		expectedUpdatedUserDTO.setName("Romulo Godoy Sarça");
		User expectedUpdatedUser = userMapper.toModel(expectedUpdatedUserDTO);
		
		String expectedUpdatedMessage = String.format("User %s with ID %s sucessfully updated",
				expectedUpdatedUserDTO.getUserName(), expectedUpdatedUserDTO.getId());

		when(userRepository.findById(expectedUpdatedUserDTO.getId())).thenReturn(Optional.of(expectedUpdatedUser));
		when(userRepository.save(any(User.class))).thenReturn(expectedUpdatedUser);

		MessageDTO creationMessage = userService.update(expectedUpdatedUserDTO.getId(), expectedUpdatedUserDTO);

		assertThat(expectedUpdatedMessage, is(equalTo(creationMessage.getMessage())));
	}
	
	@Test
	public void whenNotExistingUserIsInformedThenAnExceptionShouldBeThrown() {
		UserDTO expectedUpdatedUserDTO = userDTOBuilder.buildUserDTO();
		
		when(userRepository.findById(expectedUpdatedUserDTO.getId())).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.update(expectedUpdatedUserDTO.getId(), expectedUpdatedUserDTO)); 
	}
}
