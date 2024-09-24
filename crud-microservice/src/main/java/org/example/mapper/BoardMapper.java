package org.example.mapper;

import org.example.model.dto.BoardDto;
import org.example.model.entity.BoardEntity;
import org.example.model.entity.TodoEntity;

import java.util.ArrayList;

public class BoardMapper {
    public static BoardDto ToBoardDto(BoardEntity boardEntity) {
        return new BoardDto(boardEntity.getId(), boardEntity.getUserChatId(), boardEntity.getTitle());
    }

    public static BoardEntity ToBoardEntity(BoardDto boardDto) {
        return new BoardEntity(boardDto.getId(), boardDto.getUserChatId(), boardDto.getTitle(), new ArrayList<TodoEntity>());
    }
}
