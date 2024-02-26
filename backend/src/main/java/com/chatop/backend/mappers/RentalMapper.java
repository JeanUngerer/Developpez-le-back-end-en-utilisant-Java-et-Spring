package com.chatop.backend.mappers;

import com.chatop.backend.dtos.CreateRentalDTO;
import com.chatop.backend.dtos.RentalDTO;
import com.chatop.backend.entities.RentalEntity;
import com.chatop.backend.helpers.CycleAvoidingMappingContext;
import com.chatop.backend.models.Message;
import com.chatop.backend.models.Rental;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.WARN,
  typeConversionPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {UserMapper.class, MessageMapper.class})
@Named("RentalMapper")
public interface RentalMapper {

  @Mapping(source = "owner.id",target = "owner_id")
  RentalDTO modelToDto(Rental model);


  List<RentalDTO> modelsToDtos(List<Rental> models);

  //@Mapping(target = "owner", ignore = true)
  Rental dtoToModel(RentalDTO dto);


  List<Rental> dtosToModels(List<RentalDTO> dtos);

  @Mapping(source = "owner", target = "owner", qualifiedByName = {"UserMapper", "toEntityWithoutRentals"})
  RentalEntity modelToEntity(Rental model);


  List<RentalEntity> modelsToEntities(List<Rental> models);

  @Mapping(source = "owner", target = "owner", qualifiedByName = {"UserMapper", "toModelWithoutRentals"})
  Rental entityToModel(RentalEntity entity);


  List<Rental> entitiesToModel(List<RentalEntity> entities);

  @Named("createDtoToDto")
  @Mapping(target = "picture", ignore = true)
  Rental createDtoToModel(CreateRentalDTO createDto);

  @Named("toModelWithoutMessages")
  @Mapping(target = "messages", ignore = true)
  Rental toModelWithoutMessages(RentalEntity entity);

  @Named("modeltoModelWithoutMessages")
  @Mapping(target = "messages", ignore = true)
  Rental modeltoModelWithoutMessages(Rental model);


  @Named("toEntityWithoutMessages")
  @Mapping(target = "messages", ignore = true)
  RentalEntity toEntityWithoutMessages(Rental model);

  @Named("toDtoWithoutMessages")
  RentalDTO toDtoWithoutMessages(Rental model);

  @Named("toModelFromDtoWithoutMessages")
  @Mapping(target = "messages", ignore = true)
  Rental toModelFromDtoWithoutMessages(RentalDTO dto);



  @Named("toModelsWithoutMessages")
  @Mapping(target = ".", qualifiedByName = "toModelWithoutMessages")
  List<Rental> toModelsWithoutMessages(List<RentalEntity> entities);

  @Named("toModelsFromDtosWithoutMessages")
  @Mapping(target = ".", qualifiedByName = "toModelFromDtoWithoutMessages")
  List<Rental> toModelsFromDtosWithoutMessages(List<RentalDTO> dtos);

  @Named("toEntitiesWithoutMessages")
  @Mapping(target = ".", qualifiedByName = "toEntityWithoutMessages")
  List<RentalEntity> toEntitiesWithoutMessages(List<Rental> models);

  @Named("toDtosWithoutMessages")
  @Mapping(target = ".", qualifiedByName = "toDtoWithoutMessages")
  List<RentalDTO> toDtosWithoutMessages(List<Rental> models);



  @Named("modelsToModelsWithoutMessages")
  @Mapping(target = ".", qualifiedByName = "modelToModelWithoutMessages")
  List<Rental> modelsToModelsWithoutMessages(List<Rental> models);

  @Named("modelToModelWithoutMessages")
  @Mapping(source = "messages", target = "messages", ignore = true)
  Rental modelToModelWithoutMessages(Rental model);


  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateFromModel(Rental model, @MappingTarget RentalEntity entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateFromDto(RentalDTO dto, @MappingTarget Rental model, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);
}
