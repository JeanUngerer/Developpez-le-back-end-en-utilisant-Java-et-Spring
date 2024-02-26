package com.chatop.backend.controllers;

import com.chatop.backend.services.ImageService;
import com.nimbusds.jose.util.IOUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("images")
public class ImageController {

  private ImageService imageService;

  @GetMapping("/{name}")
  public ResponseEntity<InputStreamResource> getImageByName(@PathVariable("name") String name) {
    MediaType contentType = imageService.getContentTypeFromName(name);
    InputStreamResource inputStreamResource = new InputStreamResource(imageService.getImageByName(name));
    return ResponseEntity.ok()
      .contentType(contentType)
      .body(inputStreamResource);
  }

}
