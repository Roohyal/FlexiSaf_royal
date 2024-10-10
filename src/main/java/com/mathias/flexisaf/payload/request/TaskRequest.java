package com.mathias.flexisaf.payload.request;

import com.mathias.flexisaf.enums.Priority;
import com.mathias.flexisaf.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskRequest {
    private String taskName;

    private String taskDescription;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private LocalDate deadline;

    private Long userId;
}
