package com.mathias.flexisaf.service;

import com.mathias.flexisaf.entity.Task;
import com.mathias.flexisaf.payload.request.TaskRequest;
import com.mathias.flexisaf.payload.request.TaskUpdateRequest;
import com.mathias.flexisaf.payload.response.TaskListResponse;
import com.mathias.flexisaf.payload.response.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskRequest taskRequest, String email);

    void deleteTask(Long taskId);

    List<TaskListResponse> getAllTasks(String email);

    void updateTask(TaskUpdateRequest taskRequest, String email, Long id);


}
