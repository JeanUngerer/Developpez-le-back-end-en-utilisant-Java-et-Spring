package com.chatop.backend.dtos;

import java.time.LocalDateTime;
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
public class RentalDTO {

Long id;
String name;
String picture;
String description;
Double surface;
Double price;
LocalDateTime created_at;
LocalDateTime updated_at;
Long owner_id;

}
