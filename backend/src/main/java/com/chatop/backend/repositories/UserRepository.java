package com.chatop.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chatop.backend.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String email);
}
