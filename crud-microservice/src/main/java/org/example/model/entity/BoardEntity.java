package org.example.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "board")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userChatId;

    private String title;
}

