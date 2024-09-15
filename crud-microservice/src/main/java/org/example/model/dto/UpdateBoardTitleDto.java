package org.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UpdateBoardTitleDto {
    private Long id;
    private String title;

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }
}
