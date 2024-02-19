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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
@Transactional
public class RentalService {

  AppProperties appProperties;
	RentalRepository rentalRepository;


	RentalMapper rentalMapper;

	public List<Rental> findAllRental() {
		try {
			log.info("findAllRental");
			List<Rental> rentalList = new ArrayList<Rental>();
			rentalRepository.findAll().forEach(ct -> rentalList.add(rentalMapper.entityToModel(ct)));
			return rentalList;
		} catch (Exception e) {
			log.error("We could not find all rental: " + e.getMessage());
			throw new ExceptionHandler("We could not find your rentals");
		}
	}

	public Rental findRentalById(Long id) {
		try {
			log.info("findRentalById - id: " + id.toString());
			Rental rental = rentalMapper.entityToModel(rentalRepository.findById(id).orElseThrow(()
				-> new ExceptionHandler("We didn't find your rental")));
			return rental;
		} catch (Exception e) {
			log.error("We could not find all rental: " + e.getMessage());
			throw new ExceptionHandler("We could not find your rental");
		}
	}

	public Rental createRental(CreateRentalDTO dto) {
		try {
			log.info("createRental");

      String fileName;
      Path outputFilePath;
      String filePath;
      fileName = dto.getPicture().getOriginalFilename();

      // folderPath
      String folderPath = appProperties.getBase().getPath();

      String uniquefilename = saveImageToStorage(folderPath, dto.getPicture());

      /*
      File directory = new File(folderPath);
      if (!directory.exists()){
        directory.mkdir();
      }
      filePath = folderPath + "/" + fileName;
      outputFilePath = Paths.get(filePath);
      //Files.createFile(outputFilePath);
      File file = new File(filePath);
      OutputStream os = new FileOutputStream(file);
      os.write(dto.getPicture().getBytes());
      os.close();
*/

			Rental rental = rentalMapper.dtoToModel(rentalMapper.createDtoToDto(dto));
      rental.setPicture(uniquefilename);
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
			rentalRepository.delete(rentalMapper.modelToEntity(rental));
			return "Rental deleted";
		} catch (Exception e) {
			log.error("Couldn't delete rental: " + e.getMessage());
			throw new ExceptionHandler("We could not delete your rental");
		}
	}

  // Delete an image
  private String deleteImage(String imageDirectory, String imageName) throws IOException {
    Path imagePath = Path.of(imageDirectory, imageName);

    if (Files.exists(imagePath)) {
      Files.delete(imagePath);
      return "Success";
    } else {
      return "Failed"; // Handle missing images
    }
  }

  // Save image in a local directory
  private String saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException {
    String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

    Path uploadPath = Path.of(uploadDirectory);
    Path filePath = uploadPath.resolve(uniqueFileName);

    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

    return uniqueFileName;
  }
}
