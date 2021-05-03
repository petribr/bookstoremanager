package com.petribr.bookstoremanager.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.petribr.bookstoremanager.user.dto.MessageDTO;
import com.petribr.bookstoremanager.user.dto.UserDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Users management")
public interface UserControllerDocs {

	@ApiOperation(value = "User creation operation")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Success User creation"),
			@ApiResponse(code = 400, message = "Missing required fileds, wrong field range value or User already registered on system") })
	MessageDTO create(UserDTO userDTO);

	@ApiOperation(value = "Find User by id operation")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success User found"),
			@ApiResponse(code = 404, message = "User not found error code") })
	UserDTO findById(@PathVariable Long id);

	@ApiOperation(value = "List all registered Users")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success User found") })
	List<UserDTO> findAll();

	@ApiOperation(value = "Delete User by id operation")
	@ApiResponses(value = { 
			@ApiResponse(code = 204, message = "Success User deleted"),
			@ApiResponse(code = 404, message = "User not found error code") })
	void delete(@PathVariable Long id);
}
