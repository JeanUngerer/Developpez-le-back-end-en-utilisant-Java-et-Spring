package com.chatop.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chatop.backend.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
