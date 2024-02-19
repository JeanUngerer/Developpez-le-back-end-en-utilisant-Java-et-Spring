package com.chatop.backend.mappers;

import com.chatop.backend.dtos.UserInfoDTO;
import com.chatop.backend.entities.RentalEntity;
import com.chatop.backend.helpers.CycleAvoidingMappingContext;
import com.chatop.backend.models.Rental;
import org.mapstruct.*;

import com.chatop.backend.entities.UserEntity;
import com.chatop.backend.dtos.UserDTO;
import com.chatop.backend.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.WARN,
	typeConversionPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {MessageMapper.class, RentalMapper.class})
@Named("UserMapper")
public interface UserMapper {


  @Mapping(source = "messages", target = "messages",  qualifiedByName = {"MessageMapper", "toDtosWithoutRentalsAndUsers"})
  @Mapping(source = "rentals", target = "rentals",  qualifiedByName = {"RentalMapper", "toDtosWithoutMessages"})
	UserDTO modelToDto(User model);

	List<UserDTO> modelsToDtos(List<User> models);

  @Mapping(source = "messages", target = "messages",  qualifiedByName = {"MessageMapper", "toModelsFromDtosWithoutRentalsAndUsers"})
  @Mapping(source = "rentals", target = "rentals",  qualifiedByName = {"RentalMapper", "toModelsFromDtosWithoutMessages"})
	User dtoToModel(UserDTO dto);

	List<User> dtosToModels(List<UserDTO> dtos);

  @Mapping(source = "messages", target = "messages",  qualifiedByName = {"MessageMapper", "toEntitiesWithoutRentalsAndUsers"})
  @Mapping(source = "rentals", target = "rentals",  qualifiedByName = {"RentalMapper", "toEntitiesWithoutMessages"})
	UserEntity modelToEntity(User model);

	List<UserEntity> modelsToEntities(List<User> models);

  @Mapping(source = "messages", target = "messages",  qualifiedByName = {"MessageMapper", "toModelsWithoutRentalsAndUsers"})
  @Mapping(source = "rentals", target = "rentals",  qualifiedByName = {"RentalMapper", "toModelsWithoutMessages"})
	User entityToModel(UserEntity entity);

	List<User> entitiesToModel(List<UserEntity> entities);

  @Named("toModelWithoutRentals")
  @Mapping(target = "rentals", ignore = true)
  @Mapping(target = "messages", ignore = true)
  User toModelWithoutRentals(UserEntity entity);

  @Named("modeltoModelWithoutRentals")
  @Mapping(target = "rentals", ignore = true)
  @Mapping(target = "messages", ignore = true)
  User modeltoModelWithoutRentals(User model);

  @Named("toModelFromDtoWithoutRentals")
  @Mapping(target = "rentals", ignore = true)
  @Mapping(target = "messages", ignore = true)
  User toModelFromDtoWithoutRentals(UserEntity entity);

  @Named("toEntityWithoutRentals")
  @Mapping(target = "rentals", ignore = true)
  @Mapping(target = "messages", ignore = true)
  UserEntity toEntityWithoutRentals(User model);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromModel(User model, @MappingTarget UserEntity entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromDto(UserDTO dto, @MappingTarget User model, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

  UserInfoDTO dtoToUserInfoDto(UserDTO dto);
}
