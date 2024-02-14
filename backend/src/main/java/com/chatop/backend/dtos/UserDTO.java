package com.chatop.backend.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.chatop.backend.models.Message;
import com.chatop.backend.models.Rental;
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
public class UserDTO {

Long id;
String name;
String email;
String password;
LocalDateTime created_at;
LocalDateTime updated_at;
List<Rental> rentals;
List<Message> messages;

}
