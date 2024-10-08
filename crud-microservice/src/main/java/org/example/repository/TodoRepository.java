package org.example.repository;

import org.example.model.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    @Override
    Optional<TodoEntity> findById(Long id);

}
