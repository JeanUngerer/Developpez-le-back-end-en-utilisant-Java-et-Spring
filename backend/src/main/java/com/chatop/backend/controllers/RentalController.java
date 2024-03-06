package com.chatop.backend.controllers;

import com.chatop.backend.auth.service.TokenService;
import com.chatop.backend.dtos.CreateRentalDTO;
import com.chatop.backend.dtos.RentalDTO;
import com.chatop.backend.dtos.RentalsDTO;
import com.chatop.backend.dtos.TextResponseDTO;
import com.chatop.backend.mappers.RentalMapper;
import com.chatop.backend.services.RentalService;
import com.chatop.backend.services.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("rentals")
public class RentalController {

	@Autowired
	RentalService rentalService;

  UserService userService;

  TokenService tokenService;

	private RentalMapper rentalMapper;

	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	@GetMapping("")
	public ResponseEntity<RentalsDTO> getRentals() {
		return ResponseEntity.ok(new RentalsDTO(rentalMapper.modelsToDtos(rentalService.findAllRental())));
	}

	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<RentalDTO> getRentalById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(rentalMapper.modelToDto(rentalService.findRentalById(id)));
	}

	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	@PostMapping("")
	public ResponseEntity<TextResponseDTO> create(@RequestHeader("Authorization") String requestTokenHeader, @ModelAttribute CreateRentalDTO rentalDto) {
    rentalDto.setOwner(userService.findByEmail(tokenService.decodeTokenUsername(requestTokenHeader)));
    rentalService.createRental(rentalDto);
		return ResponseEntity.ok(new TextResponseDTO("Rental created !"));
	}

	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<TextResponseDTO> update(@RequestBody RentalDTO rentalDto, @PathVariable("id") Long id) {
    rentalDto.setId(id);
    rentalService.updateRental(rentalDto);
		return ResponseEntity.ok(new TextResponseDTO("Rental updated !"));
	}

	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		return ResponseEntity.ok(rentalService.deleteRental(id));
	}
}
