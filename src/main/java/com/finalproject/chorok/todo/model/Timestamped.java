package com.finalproject.chorok.todo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {


    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime lastWorkTime;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDay;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDay;



}
