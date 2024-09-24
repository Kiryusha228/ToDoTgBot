package org.example.service;


import org.example.model.dto.LastMessageDto;

public interface LastMessageService {
    void createLastMessage(Long userChatId);
    void setLastMessage(LastMessageDto lastMessageDto);
    Integer getLastMessageId(Long userChatId);
}
