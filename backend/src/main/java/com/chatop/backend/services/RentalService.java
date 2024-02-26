package com.chatop.backend.services;

import com.chatop.backend.auth.config.AppProperties;
import com.chatop.backend.dtos.CreateRentalDTO;
import com.chatop.backend.dtos.RentalDTO;
import com.chatop.backend.exception.ExceptionHandler;
import com.chatop.backend.helpers.CycleAvoidingMappingContext;
import com.chatop.backend.mappers.RentalMapper;
import com.chatop.backend.models.Rental;
import com.chatop.backend.repositories.RentalRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
@Transactional
public class RentalService {

  AppProperties appProperties;
	RentalRepository rentalRepository;

  ImageService imageService;


	RentalMapper rentalMapper;

	public List<Rental> findAllRental() {
		try {
			log.info("findAllRental");
      List<Rental> allRentals = rentalMapper.entitiesToModel(rentalRepository.findAll());
      return allRentals;
		} catch (Exception e) {
			log.error("We could not find all rental: " + e.getMessage());
			throw new ExceptionHandler("We could not find your rentals");
		}
	}

	public Rental findRentalById(Long id) {
		try {
			log.info("findRentalById - id: " + id.toString());
			Rental rental = rentalMapper.entityToModel(rentalRepository.findById(id).orElseThrow(()
				-> new ExceptionHandler("We didn't find your rental", HttpStatus.NOT_FOUND)));
			return rental;
		} catch (Exception e) {
			log.error("We could not find all rental: " + e.getMessage());
			throw new ExceptionHandler("We could not find your rental");
		}
	}

	public Rental createRental(CreateRentalDTO dto) {
		try {
			log.info("createRental");

      // folderPath
      String folderPath = appProperties.getBase().getPath();

      String uniquefileName = imageService.saveImageToStorage(folderPath, dto.getPicture());
      uniquefileName = "http://localhost:8080/api/images/" + uniquefileName.replace("\\", "/");

			Rental rental = rentalMapper.createDtoToModel(dto);
      rental.setPicture(uniquefileName);
      rental.setCreated_at(LocalDateTime.now());
      rental.setUpdated_at(LocalDateTime.now());
			rentalRepository.save(rentalMapper.modelToEntity(rental));
			return rental;
		} catch (Exception e) {
			log.error("Couldn't create rental: " + e.getMessage());
			throw new ExceptionHandler("We could not create your rental");
		}
	}

	public Rental updateRental(RentalDTO dto) {
		try {
			log.info("updateRental - id: " + dto.getId().toString());
			Rental rental = rentalMapper.entityToModel(rentalRepository.findById(dto.getId()).orElseThrow(()
				-> new ExceptionHandler("We could not find your rental")));
			rentalMapper.updateFromDto(dto, rental, new CycleAvoidingMappingContext());
      rental.setUpdated_at(LocalDateTime.now());
			rentalRepository.save(rentalMapper.modelToEntity(rental));
			return rental;
		} catch (Exception e) {
			log.error("Couldn't update user: " + e.getMessage());
			throw new ExceptionHandler("We could not update your rental");
		}
	}

	public String deleteRental(Long id) {
		try {
			log.info("deleteRental - id: " + id.toString());
			Rental rental = rentalMapper.entityToModel(rentalRepository.findById(id).orElseThrow(()
				-> new ExceptionHandler("We could not find your rental")));
      List<String> imagePath = List.of(rental.getPicture().split("/"));
      imageService.deleteImageByName(imagePath.get(imagePath.size()-1));
			rentalRepository.delete(rentalMapper.modelToEntity(rental));
			return "Rental deleted";
		} catch (Exception e) {
			log.error("Couldn't delete rental: " + e.getMessage());
			throw new ExceptionHandler("We could not delete your rental");
		}
	}



}
