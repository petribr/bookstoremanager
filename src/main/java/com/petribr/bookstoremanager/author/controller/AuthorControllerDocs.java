package com.petribr.bookstoremanager.author.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.petribr.bookstoremanager.author.dto.AuthorDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Authors management")
public interface AuthorControllerDocs {
	
	@ApiOperation(value = "Author creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success author creation"),
            @ApiResponse(code = 400, message = "Missing required fileds, wrong field range value or author already registered on system")
    })
    AuthorDTO create(AuthorDTO authorDTO);
	
	@ApiOperation(value = "Find author by id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success author found"),
            @ApiResponse(code = 404, message = "Author not found error code")
    })
	AuthorDTO findById(@PathVariable Long id);
	
	@ApiOperation(value = "List all registered authors")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success author found")
    })
	List<AuthorDTO> findAll();

	
	@ApiOperation(value = "Delete author by id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success author deleted"),
            @ApiResponse(code = 404, message = "Author not found error code")
    })
	void delete(@PathVariable Long id);
}
