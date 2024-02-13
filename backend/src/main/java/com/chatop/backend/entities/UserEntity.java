package com.chatop.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  Long id;

  @Column(name = "name")
  String name;

  @Column(name = "email")
  String email;

  @Column(name = "password")
  String password;

  @Column(name = "created_at", nullable = false)
  LocalDateTime created_at;

  @Column(name = "updated_at", nullable = false)
  LocalDateTime updated_at;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "rental_id")
  List<RentalEntity> rentals;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "message_id")
  List<MessageEntity> messages;
}
