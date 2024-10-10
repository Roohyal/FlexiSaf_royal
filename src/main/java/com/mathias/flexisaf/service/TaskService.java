package com.mathias.flexisaf.service;

import com.mathias.flexisaf.payload.request.TaskRequest;
import com.mathias.flexisaf.payload.response.TaskResponse;

public interface TaskService {
    TaskResponse createTask(TaskRequest taskRequest);

    void deleteTask(Long taskId);

}
