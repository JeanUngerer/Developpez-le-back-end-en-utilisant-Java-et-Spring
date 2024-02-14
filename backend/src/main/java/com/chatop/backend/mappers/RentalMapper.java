package com.chatop.backend.mappers;

import com.chatop.backend.helpers.CycleAvoidingMappingContext;
import org.mapstruct.*;

import com.chatop.backend.entities.RentalEntity;
import com.chatop.backend.dtos.RentalDTO;
import com.chatop.backend.models.Rental;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.WARN, unmappedTargetPolicy = ReportingPolicy.WARN,
	typeConversionPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RentalMapper {

	RentalDTO modelToDto(Rental model);

	List<RentalDTO> modelsToDtos(List<Rental> models);

	Rental dtoToModel(RentalDTO dto);

	List<Rental> dtosToModels(List<RentalDTO> dtos);

	RentalEntity modelToEntity(Rental model);

	List<RentalEntity> modelsToEntities(List<Rental> models);

	Rental entityToModel(RentalEntity entity);

	List<Rental> entitiesToModel(List<RentalEntity> entities);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromModel(Rental model, @MappingTarget RentalEntity entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	void updateFromDto(RentalDTO dto, @MappingTarget Rental model, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);
}
