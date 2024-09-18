package org.example.service;

import org.example.model.dto.TodoDto;

import java.util.List;

public interface TodoService {
    List<TodoDto> getTodosByBoardId(Long boardId);
    void switchTodoDone(Long todoId);
    void deleteTodo(Long todoId);
    void updateTodoInfo(Long todoId, String title, String description);
    void addTodo(TodoDto todoDto);

}
