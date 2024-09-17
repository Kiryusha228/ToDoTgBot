package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.BoardDto;
import org.example.model.dto.UpdateBoardTitleDto;
import org.example.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

    @Autowired
    private final BoardService boardService;

    @PostMapping("/add")
    public ResponseEntity addBoard(@RequestBody BoardDto boardDto) {
        boardService.createBoard(boardDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<BoardDto> getBoardByUserChatId(Long userChatId) {
        return boardService.getBoardsByChatId(userChatId);
    }

    @PatchMapping("/update")
    public ResponseEntity updateBoardTitle(@RequestBody UpdateBoardTitleDto updateBoardTitleDto) {
        boardService.updateBoardTitle(updateBoardTitleDto.getTitle(), updateBoardTitleDto.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteBoard(Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }

}
