package com.chatop.backend.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MESSAGES")
public class MessageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  Long id;

  @Column(name = "message")
  String message;

  @Column(name = "created_at", nullable = false)
  LocalDateTime created_at;

  @Column(name = "updated_at", nullable = false)
  LocalDateTime updated_at;

  @ManyToOne
  UserEntity user;

  @ManyToOne
  RentalEntity rental;

}
