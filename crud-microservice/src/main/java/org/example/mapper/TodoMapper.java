package org.example.mapper;

import org.example.model.dto.TodoDto;
import org.example.model.entity.BoardEntity;
import org.example.model.entity.TodoEntity;

public class TodoMapper {
    public static TodoDto ToTodoDto(TodoEntity todoEntity) {
        return new TodoDto(todoEntity.getId(), todoEntity.getBoard().getId(),
                todoEntity.getTitle(), todoEntity.getDescription(),
                todoEntity.getDone());
    }

    public static TodoEntity ToTodoEntity(TodoDto todoDto, BoardEntity board) {
        return new TodoEntity(todoDto.getId(), todoDto.getTitle(),
                todoDto.getDescription(), todoDto.getDone(), board);
    }
}
