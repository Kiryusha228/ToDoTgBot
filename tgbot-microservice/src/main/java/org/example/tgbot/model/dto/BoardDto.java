package org.example.tgbot.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoardDto {
    private Long id;
    private Long userChatId;
    private String title;
}
