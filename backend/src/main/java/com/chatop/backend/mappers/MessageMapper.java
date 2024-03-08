package com.chatop.backend.mappers;

import com.chatop.backend.helpers.CycleAvoidingMappingContext;
import org.mapstruct.*;

import com.chatop.backend.entities.MessageEntity;
import com.chatop.backend.dtos.MessageDTO;
import com.chatop.backend.models.Message;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.WARN,
	typeConversionPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {RentalMapper.class, UserMapper.class})
@Named("MessageMapper")
public interface MessageMapper {

  @Mapping(target = "rental_id", qualifiedByName = {"RentalMapper", "modeltoModelWithoutMessages"}, ignore = true)
  @Mapping(target = "user_id", qualifiedByName = {"UserMapper", "modeltoModelWithoutRentals"}, ignore = true)
	MessageDTO modelToDto(Message model);


	List<MessageDTO> modelsToDtos(List<Message> models);

  @Mapping(target = "rental", qualifiedByName = {"RentalMapper", "modeltoModelWithoutMessages"}, ignore = true)
  @Mapping(target = "user", qualifiedByName = {"UserMapper", "modeltoModelWithoutRentals"}, ignore = true)
	Message dtoToModel(MessageDTO dto);


	List<Message> dtosToModels(List<MessageDTO> dtos);

  @Mapping(target = "rental", qualifiedByName = {"RentalMapper", "toEntityWithoutMessages"})
  @Mapping(target = "user", qualifiedByName = {"UserMapper", "toEntityWithoutRentals"})
	MessageEntity modelToEntity(Message model);


	List<MessageEntity> modelsToEntities(List<Message> models);

  @Mapping(target = "rental", qualifiedByName = {"RentalMapper", "toModelWithoutMessages"})
  @Mapping(target = "user", qualifiedByName = {"UserMapper", "toModelWithoutRentals"})
	Message entityToModel(MessageEntity entity);

  @Named("toModelsWithoutRentalsAndUsers")
  @Mapping(target = ".", qualifiedByName = "toModelWithoutRentalsAndUsers")
  List<Message> toModelsWithoutRentalsAndUsers(List<MessageEntity> entity);

  @Named("toModelWithoutRentalsAndUsers")
  @Mapping(source = "user", target = "user", ignore = true)
  @Mapping(source = "rental", target = "rental", ignore = true)
  Message toModelWithoutRentalsAndUsers(MessageEntity entity);



  @Named("toDtosWithoutRentalsAndUsers")
  @Mapping(target = ".", qualifiedByName = "toDtoWithoutRentalsAndUsers")
  List<MessageDTO> toDtosWithoutRentalsAndUsers(List<Message> models);

  @Named("toDtoWithoutRentalsAndUsers")
  @Mapping(source = "user.id", target = "user_id")
  @Mapping(source = "rental.id", target = "rental_id")
  MessageDTO toDtoWithoutRentalsAndUsers(Message model);



  @Named("toEntitiesWithoutRentalsAndUsers")
  @Mapping(target = ".", qualifiedByName = "toEntityWithoutRentalsAndUsers")
  List<MessageEntity> toEntitiesWithoutRentalsAndUsers(List<Message> models);

  @Named("toEntityWithoutRentalsAndUsers")
  @Mapping(source = "user", target = "user", ignore = true)
  @Mapping(source = "rental", target = "rental", ignore = true)
  MessageEntity toEntityWithoutRentalsAndUsers(MessageEntity model);


  @Named("toModelsFromDtosWithoutRentalsAndUsers")
  @Mapping(target = ".", qualifiedByName = "toModelFromDtoWithoutRentalsAndUsers")
  List<Message> toModelsFromDtosWithoutRentalsAndUsers(List<MessageDTO> dtos);

  @Named("toModelFromDtoWithoutRentalsAndUsers")
  @Mapping(source = "user_id", target = "user", ignore = true)
  @Mapping(source = "rental_id", target = "rental", ignore = true)
  Message toModelFromDtoWithoutRentalsAndUsers(MessageDTO dto);

  @Named("modelsToModelsWithoutRentalsAndUsers")
  @Mapping(target = ".", qualifiedByName = "modelToModelWithoutRentalsAndUsers")
  List<Message> modelsToModelsWithoutRentalsAndUsers(List<Message> models);

  @Named("modelToModelWithoutRentalsAndUsers")
  @Mapping(source = "user", target = "user", ignore = true)
  @Mapping(source = "rental", target = "rental", ignore = true)
  Message modelToModelWithoutRentalsAndUsers(Message model);


	List<Message> entitiesToModel(List<MessageEntity> entities);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromModel(Message model, @MappingTarget MessageEntity entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromDto(MessageDTO dto, @MappingTarget Message model, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);
}
