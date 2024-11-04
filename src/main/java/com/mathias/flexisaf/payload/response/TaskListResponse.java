package com.mathias.flexisaf.payload.response;

import com.mathias.flexisaf.enums.Priority;
import com.mathias.flexisaf.enums.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskListResponse {
    private String taskName;

    private String taskDescription;

    private LocalDate deadline;

    private Status taskStatus;

    private Priority priority;



}
