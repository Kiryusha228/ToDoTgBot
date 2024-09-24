package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.mapper.BoardMapper;
import org.example.model.dto.BoardDto;
import org.example.repository.BoardRepository;
import org.example.service.BoardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    public List<BoardDto> getBoardsByChatId(Long chatId) {

        var boards = boardRepository.findAll().stream()
                .filter(board -> board.getUserChatId().equals(chatId)).toList();

        return boards.stream().map(BoardMapper::ToBoardDto).toList();
    }

    @Override
    public BoardDto getBoardById(Long boardId) {
        return BoardMapper.ToBoardDto(boardRepository.findById(boardId).get());
    }

    @Override
    public void createBoard(BoardDto boardDto) {

        boardRepository.save(BoardMapper.ToBoardEntity(boardDto));
    }

    @Override
    public void updateBoardTitle(String title, Long boardId) {
        var board = boardRepository.findById(boardId);
        var boardEntity = board.get();
        boardEntity.setTitle(title);
        boardRepository.save(boardEntity);

    }

    @Override
    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
