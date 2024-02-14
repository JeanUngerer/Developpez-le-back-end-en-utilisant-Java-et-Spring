package com.chatop.backend.mappers;

import com.chatop.backend.helpers.CycleAvoidingMappingContext;
import org.mapstruct.*;

import com.chatop.backend.entities.UserEntity;
import com.chatop.backend.dtos.UserDTO;
import com.chatop.backend.models.User;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.WARN,
	typeConversionPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

	UserDTO modelToDto(User model);

	List<UserDTO> modelsToDtos(List<User> models);

	User dtoToModel(UserDTO dto);

	List<User> dtosToModels(List<UserDTO> dtos);

	UserEntity modelToEntity(User model);

	List<UserEntity> modelsToEntities(List<User> models);

	User entityToModel(UserEntity entity);

	List<User> entitiesToModel(List<UserEntity> entities);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromModel(User model, @MappingTarget UserEntity entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	void updateFromDto(UserDTO dto, @MappingTarget User model, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);
}
