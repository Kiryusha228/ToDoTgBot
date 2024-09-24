package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.BoardDto;
import org.example.model.dto.LastMessageDto;
import org.example.service.LastMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("message")
public class LastMessageController {
    @Autowired
    private final LastMessageService lastMessageService;

    @PutMapping("/set")
    public ResponseEntity setLastMessage(@RequestBody LastMessageDto lastMessageDto) {
        lastMessageService.setLastMessage(lastMessageDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Integer getLastMessage(Long userChatId) {
        return lastMessageService.getLastMessageId(userChatId);
    }

    @PostMapping("/create")
    public ResponseEntity getBoardById(Long userChatId) {
        lastMessageService.createLastMessage(userChatId);
        return ResponseEntity.ok().build();
    }
}
