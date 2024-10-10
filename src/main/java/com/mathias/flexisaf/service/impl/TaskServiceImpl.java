package com.mathias.flexisaf.service.impl;

import com.mathias.flexisaf.entity.Person;
import com.mathias.flexisaf.entity.Task;
import com.mathias.flexisaf.payload.request.TaskRequest;
import com.mathias.flexisaf.payload.response.TaskResponse;
import com.mathias.flexisaf.repository.PersonRepository;
import com.mathias.flexisaf.repository.TaskRepository;
import com.mathias.flexisaf.service.TaskService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;


    @Override
    public TaskResponse createTask(TaskRequest taskRequest) {
        // Fetch the user based on the provided user ID
        Person user = personRepository.findById(taskRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = Task.builder()
                .taskName(taskRequest.getTaskName())
                .taskDescription(taskRequest.getTaskDescription())
                .priority(taskRequest.getPriority())
                .status(taskRequest.getStatus())
                .deadline(taskRequest.getDeadline())
                .user(user)
                .build();

        taskRepository.save(task);
        return TaskResponse.builder()
                .responseCode("003")
                .responseMessage("Task created")
                .build();
    }

    @Override
    public void deleteTask(Long taskId) {
        if(!taskRepository.existsById(taskId)) {
            throw new RuntimeException("Task not found");
        }
        taskRepository.deleteById(taskId);
    }
}
