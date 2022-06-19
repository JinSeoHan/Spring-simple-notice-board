package com.kw4.dto;

import com.kw4.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter // Annotation that automatically generates getter for all fields
@Setter // Annotation that automatically creates a setter for all fields
@ToString // Automatically create the toString method for a field
@NoArgsConstructor // Add default constructor without parameters
public class BoardDto {
    private Long id;

    private String title;
    private String content;
    private int views;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public BoardEntity toEntity(){
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .title(title)
                .content(content)
                .views(views)
                .build();
        return boardEntity;
    }

    @Builder // The stability can be ensured by using the builder pattern instead of using the annotation and setter that creates the builder pattern class
    public BoardDto(Long id, String title, String content, int views, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.views = views;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

}
