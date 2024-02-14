package com.chatop.backend.controllers;

import com.chatop.backend.dtos.UserDTO;
import com.chatop.backend.mappers.UserMapper;
import com.chatop.backend.services.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;

	private UserMapper userMapper;

	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getUsers() {
		return ResponseEntity.ok(userMapper.modelsToDtos(userService.findAllUser()));
	}

	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(userMapper.modelToDto(userService.findUserById(id)));
	}

	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDto) {
		return ResponseEntity.ok(userMapper.modelToDto(userService.createUser(userDto)));
	}

	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	@PutMapping("/update")
	public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDto) {
		return ResponseEntity.ok(userMapper.modelToDto(userService.updateUser(userDto)));
	}

	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		return ResponseEntity.ok(userService.deleteUser(id));
	}
}
