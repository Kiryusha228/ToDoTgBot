package org.example.mapper;

import org.example.model.dto.TodoDto;
import org.example.model.entity.TodoEntity;

public class TodoMapper {
    public static TodoDto ToTodoDto(TodoEntity todoEntity) {
        return new TodoDto(todoEntity.getId(), todoEntity.getBoard().getId(),
                            todoEntity.getTitle(), todoEntity.getDescription());
    }
}
