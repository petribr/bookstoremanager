package com.petribr.bookstoremanager.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.petribr.bookstoremanager.user.dto.MessageDTO;
import com.petribr.bookstoremanager.user.dto.UserDTO;
import com.petribr.bookstoremanager.user.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
//@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController implements UserControllerDocs {
	private final UserService userService;

    @Autowired
    public UserController(UserService userService) { //, AuthenticationService authenticationService) {
        this.userService = userService;
        //this.authenticationService = authenticationService;
    }
    
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MessageDTO create(@RequestBody @Valid UserDTO userToCreateDTO) {
		return userService.create(userToCreateDTO);
	}

	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<UserDTO> findAll() {
		return userService.findAll();		
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO findById(@PathVariable Long id) {
		return userService.findById(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		userService.delete(id);
	}

	@PutMapping("/{id}")
	public MessageDTO update(@PathVariable Long id, @RequestBody @Valid UserDTO userTooUpdateDTO) {
		return userService.update(id, userTooUpdateDTO);
	}
}
