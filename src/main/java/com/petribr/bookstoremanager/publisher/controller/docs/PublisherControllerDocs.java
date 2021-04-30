package com.petribr.bookstoremanager.publisher.controller.docs;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.petribr.bookstoremanager.publisher.dto.PublisherDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Publishers management")
public interface PublisherControllerDocs {
	
	@ApiOperation(value = "Publisher creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success Publisher creation"),
            @ApiResponse(code = 400, message = "Missing required fileds, wrong field range value or Publisher already registered on system")
    })
    PublisherDTO create(PublisherDTO publisherDTO);
	
	@ApiOperation(value = "Find Publisher by id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success Publisher found"),
            @ApiResponse(code = 404, message = "Publisher not found error code")
    })
	PublisherDTO findById(@PathVariable Long id);
	
	@ApiOperation(value = "List all registered Publishers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success Publisher found")
    })
	List<PublisherDTO> findAll();

	
	@ApiOperation(value = "Delete Publisher by id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success Publisher deleted"),
            @ApiResponse(code = 404, message = "Publisher not found error code")
    })
	void delete(@PathVariable Long id);
}
