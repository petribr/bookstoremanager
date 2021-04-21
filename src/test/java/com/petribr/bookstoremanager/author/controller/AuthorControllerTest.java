package com.petribr.bookstoremanager.author.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.petribr.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.petribr.bookstoremanager.author.service.AuthorService;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

	@Mock
	private AuthorService authorService;
	
	@InjectMocks
	private AuthorController authorController;
	
	// Mock da parte controladora do Spring disponibilizado pelo Mockito
	private MockMvc mockMvc;
	
	private AuthorDTOBuilder authorDTOBuilder;
	
	@BeforeEach
	void setup() {
		authorDTOBuilder = AuthorDTOBuilder.builder().build();
		mockMvc = MockMvcBuilders.standaloneSetup(authorController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.setViewResolvers((s, locale) -> new MappingJackson2JsonView())
				.build();
				
	}
	
}
