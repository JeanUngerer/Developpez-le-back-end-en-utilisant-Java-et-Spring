package com.chatop.backend.mappers;

import com.chatop.backend.helpers.CycleAvoidingMappingContext;
import org.mapstruct.*;

import com.chatop.backend.entities.MessageEntity;
import com.chatop.backend.dtos.MessageDTO;
import com.chatop.backend.models.Message;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.WARN,
	typeConversionPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MessageMapper {

	MessageDTO modelToDto(Message model);

	List<MessageDTO> modelsToDtos(List<Message> models);

	Message dtoToModel(MessageDTO dto);

	List<Message> dtosToModels(List<MessageDTO> dtos);

	MessageEntity modelToEntity(Message model);

	List<MessageEntity> modelsToEntities(List<Message> models);

	Message entityToModel(MessageEntity entity);

	List<Message> entitiesToModel(List<MessageEntity> entities);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromModel(Message model, @MappingTarget MessageEntity entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	void updateFromDto(MessageDTO dto, @MappingTarget Message model, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);
}
