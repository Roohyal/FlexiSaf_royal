package com.mathias.flexisaf.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mathias.flexisaf.enums.Priority;
import com.mathias.flexisaf.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task extends BaseClass{
    private String taskName;

    private String taskDescription;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    @JsonBackReference("user")
    private Person user;

}
