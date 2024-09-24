package org.example.model.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lastMessage")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LastMessageEntity {
    @Id
    @Column(name = "userchatid")
    private Long userChatId;

    @Nullable
    @Column(name = "messageid")
    private Integer messageId;
}
