package com.chatop.backend.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {
  String name;
  String email;
  String password;
}
