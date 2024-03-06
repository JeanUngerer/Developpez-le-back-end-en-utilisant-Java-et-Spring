package com.chatop.backend.dtos;

import java.time.LocalDateTime;

import com.chatop.backend.models.Rental;
import com.chatop.backend.models.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

Long id;
String message;
LocalDateTime created_at;
LocalDateTime updated_at;
User user;
Rental rental;

}
