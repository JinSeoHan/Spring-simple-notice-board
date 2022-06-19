package com.kw4.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // access adds a property that sets the constructor's access rights, a default constructor without parameters, because it does not require an entity to be created externally
@Getter // Automatically generate getter for all fields
@Entity // Role to inform JPA that the entity to map the object to the table
@Table(name = "board") // Annotation specifying table information that maps to an entity class
public class BoardEntity extends TimeEntity{

    @Id // Indicate that this is the primary key for the table
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int views;

    @Builder // The stability can be ensured by using the builder pattern instead of using the annotation and setter that creates the builder pattern class
    public BoardEntity(Long id, String title, String content, int views) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.views = views;
    }

}
