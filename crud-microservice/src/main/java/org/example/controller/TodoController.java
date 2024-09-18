package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.BoardDto;
import org.example.model.dto.TodoDto;
import org.example.model.dto.UpdateBoardTitleDto;
import org.example.model.dto.UpdateTodoDto;
import org.example.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("todo")
public class TodoController {
    @Autowired
    private final TodoService todoService;

    @PostMapping("/add")
    public ResponseEntity addTodo(@RequestBody TodoDto todoDto) {
        todoService.addTodo(todoDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<TodoDto> getTodosByBoardId(Long boardId) {
        return todoService.getTodosByBoardId(boardId);
    }

    @PatchMapping("/update")
    public ResponseEntity updateTodoInfo(@RequestBody UpdateTodoDto updateTodoDto) {
        todoService.updateTodoInfo(updateTodoDto.getId(), updateTodoDto.getTitle(),
                updateTodoDto.getDescription());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteTodo(Long todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/switch")
    public ResponseEntity switchTodoDone(Long todoId){
        todoService.switchTodoDone(todoId);
        return ResponseEntity.ok().build();
    }
}
