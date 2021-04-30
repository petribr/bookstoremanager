package com.petribr.bookstoremanager.publisher.controller;

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

import com.petribr.bookstoremanager.publisher.builder.PublisherDTOBuilder;
import com.petribr.bookstoremanager.publisher.dto.PublisherDTO;
import com.petribr.bookstoremanager.publisher.service.PublisherService;
@ExtendWith(MockitoExtension.class)
public class PublisherControllerTest {

	private static final String PUBLISHERS_API_URL_PATH = "/api/v1/publishers";

	@Mock
	private PublisherService publisherService;
	
	@InjectMocks
	private PublisherController publisherController;
	
	// Mock da parte controladora do Spring disponibilizado pelo Mockito
	private MockMvc mockMvc;
	
	private PublisherDTOBuilder publisherDTOBuilder;
	
	@BeforeEach
	void setup() {
		publisherDTOBuilder = PublisherDTOBuilder.builder().build();
		mockMvc = MockMvcBuilders.standaloneSetup(publisherController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.setViewResolvers((s, locale) -> new MappingJackson2JsonView())
				.build();				
	}
	
	// para o código 400 vamos simular um campo como nulo
	@Test
	void whenPOSTIsCalledThenStatusCreatedShouldBeReturned() throws Exception {
				
		PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
        
		when(publisherService.create(expectedCreatedPublisherDTO))
        .thenReturn(expectedCreatedPublisherDTO);
        
		mockMvc.perform(post(PUBLISHERS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedCreatedPublisherDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(expectedCreatedPublisherDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedCreatedPublisherDTO.getName())))
                .andExpect(jsonPath("$.code", is(expectedCreatedPublisherDTO.getCode())));
	}
	
    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenBadRequestStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
        expectedCreatedPublisherDTO.setName(null);

        mockMvc.perform(post(PUBLISHERS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedCreatedPublisherDTO)))
                .andExpect(status().isBadRequest());
    }
	
    @Test
	void whenGETWithValidIdIsCalledThenStatusOkShouldBeReturned() throws Exception {
				
		PublisherDTO expectedFoundPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
        
		when(publisherService.findById(expectedFoundPublisherDTO.getId()))
        .thenReturn(expectedFoundPublisherDTO);
        
		mockMvc.perform(get(PUBLISHERS_API_URL_PATH + "/" + expectedFoundPublisherDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedFoundPublisherDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedFoundPublisherDTO.getName())))
                .andExpect(jsonPath("$.code", is(expectedFoundPublisherDTO.getCode())));
	}
    
    @Test
 	void whenGETListIsCalledThenStatusOkShouldBeReturned() throws Exception {
 				
 		PublisherDTO expectedFoundPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
         
 		when(publisherService.findAll())
         .thenReturn(Collections.singletonList(expectedFoundPublisherDTO));
         
 		mockMvc.perform(get(PUBLISHERS_API_URL_PATH)
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$[0].id", is(expectedFoundPublisherDTO.getId().intValue())))
                 .andExpect(jsonPath("$[0].name", is(expectedFoundPublisherDTO.getName())))
                 .andExpect(jsonPath("$[0].code", is(expectedFoundPublisherDTO.getCode())));
 	}
    
    
    @Test
    void whenDELETEWithValidIdCalledThenNoContentShouldBeReturned() throws Exception {
    	PublisherDTO expectedDeletedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
        
 		var id = expectedDeletedPublisherDTO.getId();
		doNothing().when(publisherService).delete(id);

 		mockMvc.perform(delete(PUBLISHERS_API_URL_PATH + "/" + id)
 				.contentType(MediaType.APPLICATION_JSON))
 				.andExpect(status().isNoContent());
    }
}
