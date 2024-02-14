package com.chatop.backend.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

  Long id;
  String name;
  String email;
  LocalDateTime created_at;
  LocalDateTime updated_at;

}
