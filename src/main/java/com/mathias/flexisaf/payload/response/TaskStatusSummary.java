package com.mathias.flexisaf.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskStatusSummary {

    private Long pending;
    private Long completed;
    private Long inProgress;
    private Long totalTasks;
}
