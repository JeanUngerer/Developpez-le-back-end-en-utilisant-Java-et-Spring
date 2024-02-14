package com.chatop.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chatop.backend.entities.MessageEntity;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
}
