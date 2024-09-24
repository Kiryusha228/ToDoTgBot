package org.example.repository;

import org.example.model.entity.LastMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LastMessageRepository extends JpaRepository<LastMessageEntity, Long> {
    @Override
    Optional<LastMessageEntity> findById(Long id);
}

