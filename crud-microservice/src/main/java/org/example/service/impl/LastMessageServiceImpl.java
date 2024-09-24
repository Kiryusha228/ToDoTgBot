package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.LastMessageDto;
import org.example.model.entity.LastMessageEntity;
import org.example.repository.LastMessageRepository;
import org.example.service.LastMessageService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LastMessageServiceImpl implements LastMessageService {
    private final LastMessageRepository lastMessageRepository;

    @Override
    public void createLastMessage(Long userCharId) {
        lastMessageRepository.save(new LastMessageEntity(userCharId, null));
    }

    @Override
    public void setLastMessage(LastMessageDto lastMessageDto) {
        var lastMessageEntity = lastMessageRepository.findById(lastMessageDto.getUserChatId()).get();
        lastMessageEntity.setMessageId(lastMessageDto.getMessageId());
        lastMessageRepository.save(lastMessageEntity);
    }

    @Override
    public Integer getLastMessageId(Long userChatId) {
        return lastMessageRepository.findById(userChatId).get().getMessageId();
    }
}
