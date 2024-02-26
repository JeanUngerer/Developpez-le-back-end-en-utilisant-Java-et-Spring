package com.chatop.backend.services;


import com.chatop.backend.auth.config.AppProperties;
import com.chatop.backend.exception.ExceptionHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
@Transactional
public class ImageService {

  AppProperties appProperties;

  public InputStream getImageByName(String imageName) {
    String folderPath = appProperties.getBase().getPath();
    InputStream inputStream = getImage(folderPath, imageName);
    return inputStream;
  }

  public String deleteImageByName(String imageName) throws IOException {
    String folderPath = appProperties.getBase().getPath();
    return deleteImage(folderPath, imageName);
  }

  public MediaType getContentTypeFromName(String name){
    return name.endsWith(".jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
  }


  public InputStream getImage(String imageDirectory, String imageName) {
    log.info("IMAGE nAME : " + imageName);
    Path imagePath = Path.of(imageDirectory, imageName);

    if (Files.exists(imagePath)) {
      try {
        File file = imagePath.toFile();
        InputStream inputStream = new FileInputStream(file);
        int result = 0;
        return inputStream;
      } catch (IOException e) {
        throw new ExceptionHandler("Error reading image", e);
      }

    } else {
      throw new ExceptionHandler("Image not found");
    }
  }


  public String deleteImage(String imageDirectory, String imageName) throws IOException {
    Path imagePath = Path.of(imageDirectory, imageName);

    if (Files.exists(imagePath)) {
      Files.delete(imagePath);
      return "Success";
    } else {
      return "Failed"; // Handle missing images
    }
  }

  // Save image in a local directory
  public String saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException {
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
