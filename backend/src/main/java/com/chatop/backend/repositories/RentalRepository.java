package com.chatop.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chatop.backend.entities.RentalEntity;

public interface RentalRepository extends JpaRepository<RentalEntity, Long> {
}
