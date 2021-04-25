package com.petribr.bookstoremanager.author.controller;

import static com.petribr.bookstoremanager.utils.JsonConversionUtils.asJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

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

import com.petribr.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.petribr.bookstoremanager.author.dto.AuthorDTO;
import com.petribr.bookstoremanager.author.service.AuthorService;
@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

	private static final String AUTHOR_API_URL_PATH = "/api/v1/authors";

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
	
	
	// para o código 400 vamos simular um campo como nulo
	@Test
	void whenPOSTIsCalledThenStatusCreatedShouldBeReturned() throws Exception {
				
		AuthorDTO expectedCreatedAuthorDTO = authorDTOBuilder.buildAuthorDTO();
        
		when(authorService.create(expectedCreatedAuthorDTO))
        .thenReturn(expectedCreatedAuthorDTO);
        
		mockMvc.perform(post(AUTHOR_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedCreatedAuthorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(expectedCreatedAuthorDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedCreatedAuthorDTO.getName())))
                .andExpect(jsonPath("$.age", is(expectedCreatedAuthorDTO.getAge())));
	}
	
    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenBadRequestStatusShouldBeInformed() throws Exception {
        AuthorDTO expectedCreatedAuthorDTO = authorDTOBuilder.buildAuthorDTO();
        expectedCreatedAuthorDTO.setName(null);

        mockMvc.perform(post(AUTHOR_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedCreatedAuthorDTO)))
                .andExpect(status().isBadRequest());
    }
	
    @Test
	void whenGETWithValidIdIsCalledThenStatusOkShouldBeReturned() throws Exception {
				
		AuthorDTO expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();
        
		when(authorService.findById(expectedFoundAuthorDTO.getId()))
        .thenReturn(expectedFoundAuthorDTO);
        
		mockMvc.perform(get(AUTHOR_API_URL_PATH + "/" + expectedFoundAuthorDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedFoundAuthorDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedFoundAuthorDTO.getName())))
                .andExpect(jsonPath("$.age", is(expectedFoundAuthorDTO.getAge())));
	}
    
    @Test
 	void whenGETListIsCalledThenStatusOkShouldBeReturned() throws Exception {
 				
 		AuthorDTO expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();
         
 		when(authorService.findAll())
         .thenReturn(Collections.singletonList(expectedFoundAuthorDTO));
         
 		mockMvc.perform(get(AUTHOR_API_URL_PATH)
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$[0].id", is(expectedFoundAuthorDTO.getId().intValue())))
                 .andExpect(jsonPath("$[0].name", is(expectedFoundAuthorDTO.getName())))
                 .andExpect(jsonPath("$[0].age", is(expectedFoundAuthorDTO.getAge())));
 	}
    
    
    @Test
    void whenDELETEWithValidIdCalledThenNoContentShouldBeReturned() throws Exception {
    	AuthorDTO expectedDeletedAuthorDTO = authorDTOBuilder.buildAuthorDTO();
        
 		var id = expectedDeletedAuthorDTO.getId();
		doNothing().when(authorService).delete(id);

 		mockMvc.perform(delete(AUTHOR_API_URL_PATH + "/" + id)
 				.contentType(MediaType.APPLICATION_JSON))
 				.andExpect(status().isNoContent());
    }
}
