package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.mapper.TodoMapper;
import org.example.model.dto.TodoDto;
import org.example.repository.BoardRepository;
import org.example.repository.TodoRepository;
import org.example.service.TodoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final BoardRepository boardRepository;

    @Override
    public List<TodoDto> getTodosByBoardId(Long boardId) {
        var todos = todoRepository.findAll().stream()
                .filter(todo -> todo.getBoard().getId().equals(boardId))
                .toList();

        return todos.stream().map(TodoMapper::ToTodoDto).toList();
    }

    @Override
    public TodoDto getTodoById(Long todoId) {
        return TodoMapper.ToTodoDto(todoRepository.findById(todoId).get());
    }

    @Override
    public void switchTodoDone(Long todoId) {
        var todo = todoRepository.findById(todoId);
        var todoEntity = todo.get();
        todoEntity.setDone(!todoEntity.getDone());
        todoRepository.save(todoEntity);
    }

    @Override
    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }

    @Override
    public void updateTodoInfo(Long todoId, String title, String description) {
        var todo = todoRepository.findById(todoId);
        var todoEntity = todo.get();
        todoEntity.setTitle(title);
        todoEntity.setDescription(description);
        todoRepository.save(todoEntity);
    }

    @Override
    public void addTodo(TodoDto todoDto) {
        var board = boardRepository.findById(todoDto.getBoardId()).get();

        todoRepository.save(TodoMapper.ToTodoEntity(todoDto, board));
    }
}
