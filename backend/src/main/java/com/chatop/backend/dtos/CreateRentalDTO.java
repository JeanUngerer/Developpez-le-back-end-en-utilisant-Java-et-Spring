package com.chatop.backend.dtos;

import com.chatop.backend.models.Message;
import com.chatop.backend.models.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRentalDTO {
  String name;
  MultipartFile picture;
  String description;
  Double surface;
  Double price;
  LocalDateTime created_at;
  LocalDateTime updated_at;
  List<Message> messages;
  User owner;
}
