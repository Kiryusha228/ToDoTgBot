package org.example.service;

import org.example.model.dto.BoardDto;

import java.util.List;

public interface BoardService {
    List<BoardDto> getBoardsByChatId(Long chatId);

    BoardDto getBoardById(Long boardId);

    void createBoard(BoardDto boardDto);

    void updateBoardTitle(String title, Long boardId);

    void deleteBoard(Long boardId);

}
