package com.chatop.backend.controllers;

import com.chatop.backend.dtos.MessageDTO;
import com.chatop.backend.mappers.MessageMapper;
import com.chatop.backend.services.MessageService;
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
@RequestMapping("messages")
public class MessageController {

	@Autowired
	MessageService messageService;

	private MessageMapper messageMapper;

	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	@GetMapping("/messages")
	public ResponseEntity<List<MessageDTO>> getMessages() {
		return ResponseEntity.ok(messageMapper.modelsToDtos(messageService.findAllMessage()));
	}

	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<MessageDTO> getMessageById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(messageMapper.modelToDto(messageService.findMessageById(id)));
	}

	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	@PostMapping("")
	public ResponseEntity<MessageDTO> create(@RequestBody MessageDTO messageDto) {
		return ResponseEntity.ok(messageMapper.modelToDto(messageService.createMessage(messageDto)));
	}

	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	@PutMapping("/update")
	public ResponseEntity<MessageDTO> update(@RequestBody MessageDTO messageDto) {
		return ResponseEntity.ok(messageMapper.modelToDto(messageService.updateMessage(messageDto)));
	}

	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		return ResponseEntity.ok(messageService.deleteMessage(id));
	}
}
