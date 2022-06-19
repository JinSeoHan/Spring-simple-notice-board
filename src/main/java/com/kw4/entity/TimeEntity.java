package com.kw4.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter // Annotation that automatically generates getter for all fields
@MappedSuperclass // Mapping to Table Annotations for inheriting mapping information to child entities
@EntityListeners(AuditingEntityListener.class) // Inform JPA that the entity uses the auditing function
public class TimeEntity {
    @CreatedDate //Inject creation date when Entity is initially saved
    @LastModifiedDate//mapping column
    private LocalDateTime createdDate;
}
