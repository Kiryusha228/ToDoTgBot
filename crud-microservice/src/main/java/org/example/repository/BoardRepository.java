package org.example.repository;

import org.example.model.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Override
    Optional<BoardEntity> findById(Long id);

}
