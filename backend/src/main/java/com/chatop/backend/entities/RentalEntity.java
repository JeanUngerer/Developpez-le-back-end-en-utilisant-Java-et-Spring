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
@Table(name = "RENTALS")
public class RentalEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  Long id;

  @Column(name = "name")
  String name;

  @Column(name = "picture")
  String picture;

  @Column(name = "description", length = 2000)
  String description;

  @Column(name = "surface")
  Double surface;

  @Column(name = "price")
  Double price;

  @Column(name = "created_at", nullable = false)
  LocalDateTime created_at;

  @Column(name = "updated_at", nullable = false)
  LocalDateTime updated_at;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "rental_id")
  List<MessageEntity> messages;

  @ManyToOne
  UserEntity owner;
}
