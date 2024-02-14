package com.chatop.backend.models;

import java.time.LocalDateTime;
import java.util.List;

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
public class Message {

Long id;
String message;
LocalDateTime created_at;
LocalDateTime updated_at;
User user;
Rental rental;

}
