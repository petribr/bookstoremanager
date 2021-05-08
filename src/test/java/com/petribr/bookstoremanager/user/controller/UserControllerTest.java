package com.petribr.bookstoremanager.user.controller;

import static com.petribr.bookstoremanager.utils.JsonConversionUtils.asJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.petribr.bookstoremanager.user.builder.UserDTOBuilder;
import com.petribr.bookstoremanager.user.dto.MessageDTO;
import com.petribr.bookstoremanager.user.dto.UserDTO;
import com.petribr.bookstoremanager.user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	private static final String USERS_API_URL_PATH = "/api/v1/users";

	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	private UserDTOBuilder userDTOBuilder;

	@BeforeEach
	void setup() {
		userDTOBuilder = UserDTOBuilder.builder().build();

		mockMvc = MockMvcBuilders.standaloneSetup(userController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.setViewResolvers((s, locale) -> new MappingJackson2JsonView()).build();
	}

	@Test
	public void WhenPOSTIsCalledThenCreatedStatusShouldBeReturned() throws Exception {
		UserDTO expectedUserToCreateDTO = userDTOBuilder.buildUserDTO();

		String expectedCreationMessage = String.format("User %s with ID %s sucessfully created",
				expectedUserToCreateDTO.getUserName(), expectedUserToCreateDTO.getId());

		MessageDTO expectedCreationMessageDTO = MessageDTO.builder().message(expectedCreationMessage).build();

		when(userService.create(expectedUserToCreateDTO)).thenReturn(expectedCreationMessageDTO);

		mockMvc.perform(post(USERS_API_URL_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(expectedUserToCreateDTO))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.message", is(expectedCreationMessage)));
	}

	@Test
	public void WhenPOSTIsCalledWithoutRequiredFieldThenBadRequestStatusShouldBeReturned() throws Exception {
		UserDTO expectedUserToCreateDTO = userDTOBuilder.buildUserDTO();
		expectedUserToCreateDTO.setUserName(null);

		mockMvc.perform(post(USERS_API_URL_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(expectedUserToCreateDTO))).andExpect(status().isBadRequest());
	}

	@Test
	void whenDELETEWithValidIdCalledThenNoContentShouldBeReturned() throws Exception {
		UserDTO expectedDeletedUserDTO = userDTOBuilder.buildUserDTO();

		var id = expectedDeletedUserDTO.getId();
		doNothing().when(userService).delete(id);

		mockMvc.perform(delete(USERS_API_URL_PATH + "/" + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	@Test
	public void WhenPUTIsCalledThenOkStatusShouldBeReturned() throws Exception {
		UserDTO expectedUserToUpdateDTO = userDTOBuilder.buildUserDTO();
		expectedUserToUpdateDTO.setUserName("petester");
		var expectedUserId = expectedUserToUpdateDTO.getId();
		String expectedUpdateMessage = String.format("User %s with ID %s sucessfully updated",
				"petester", expectedUserId);

		MessageDTO expectedUpdateMessageDTO = MessageDTO.builder().message(expectedUpdateMessage).build();

		when(userService.update(expectedUserId, expectedUserToUpdateDTO)).thenReturn(expectedUpdateMessageDTO);

		mockMvc.perform(put(USERS_API_URL_PATH + "/" + expectedUserId).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(expectedUserToUpdateDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is(expectedUpdateMessage)));
	}
}
