package com.chatop.backend.services;

import com.chatop.backend.dtos.MessageDTO;
import com.chatop.backend.exception.ExceptionHandler;
import com.chatop.backend.helpers.CycleAvoidingMappingContext;
import com.chatop.backend.mappers.MessageMapper;
import com.chatop.backend.models.Message;
import com.chatop.backend.repositories.MessageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
@Transactional
public class MessageService {

	MessageRepository messageRepository;

	MessageMapper messageMapper;

	public List<Message> findAllMessage() {
		try {
			log.info("findAllMessage");
			List<Message> messageList = new ArrayList<Message>();
			messageRepository.findAll().forEach(ct -> messageList.add(messageMapper.entityToModel(ct)));
			return messageList;
		} catch (Exception e) {
			log.error("We could not find all message: " + e.getMessage());
			throw new ExceptionHandler("We could not find your messages");
		}
	}

	public Message findMessageById(Long id) {
		try {
			log.info("findMessageById - id: " + id.toString());
			Message message = messageMapper.entityToModel(messageRepository.findById(id).orElseThrow(()
				-> new ExceptionHandler("We didn't find your message")));
			return message;
		} catch (Exception e) {
			log.error("We could not find all message: " + e.getMessage());
			throw new ExceptionHandler("We could not find your message");
		}
	}

	public Message createMessage(MessageDTO dto) {
		try {
			log.info("createMessage");
			Message message = messageMapper.dtoToModel(dto);
			messageRepository.save(messageMapper.modelToEntity(message));
			return message;
		} catch (Exception e) {
			log.error("Couldn't message user: " + e.getMessage());
			throw new ExceptionHandler("We could not create your message");
		}
	}

	public Message updateMessage(MessageDTO dto) {
		try {
			log.info("updateMessage - id: " + dto.getId().toString());
			Message message = messageMapper.entityToModel(messageRepository.findById(dto.getId()).orElseThrow(()
				-> new ExceptionHandler("We could not find your message")));
			messageMapper.updateFromDto(dto, message, new CycleAvoidingMappingContext());
			messageRepository.save(messageMapper.modelToEntity(message));
			return message;
		} catch (Exception e) {
			log.error("Couldn't update user: " + e.getMessage());
			throw new ExceptionHandler("We could not update your message");
		}
	}

	public String deleteMessage(Long id) {
		try {
			log.info("deleteMessage - id: " + id.toString());
			Message message = messageMapper.entityToModel(messageRepository.findById(id).orElseThrow(()
				-> new ExceptionHandler("We could not find your message")));
			messageRepository.delete(messageMapper.modelToEntity(message));
			return "Message deleted";
		} catch (Exception e) {
			log.error("Couldn't delete message: " + e.getMessage());
			throw new ExceptionHandler("We could not delete your message");
		}
	}
}
