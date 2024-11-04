package com.mathias.flexisaf.service.impl;

import com.mathias.flexisaf.entity.Person;
import com.mathias.flexisaf.entity.Task;
import com.mathias.flexisaf.exceptions.NotFoundException;
import com.mathias.flexisaf.payload.request.TaskRequest;
import com.mathias.flexisaf.payload.request.TaskUpdateRequest;
import com.mathias.flexisaf.payload.response.TaskListResponse;
import com.mathias.flexisaf.payload.response.TaskResponse;
import com.mathias.flexisaf.repository.PersonRepository;
import com.mathias.flexisaf.repository.TaskRepository;
import com.mathias.flexisaf.service.TaskService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final PersonRepository personRepository;


    @Override
    public TaskResponse createTask(TaskRequest taskRequest, String email) {
        // Fetch the user based on the provided email
       Person person = personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));


        Task task = Task.builder()
                .taskName(taskRequest.getTaskName())
                .taskDescription(taskRequest.getTaskDescription())
                .priority(taskRequest.getPriority())
                .status(taskRequest.getStatus())
                .deadline(taskRequest.getDeadline())
                .user(person)
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

    @Override
    public List<TaskListResponse> getAllTasks(String email) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));
        List<Task> tasks = taskRepository.findAll();
        List<TaskListResponse> taskList = new ArrayList<>();

        for (Task task1 : tasks) {
            TaskListResponse response = TaskListResponse.builder()
                    .taskName(task1.getTaskName())
                    .taskDescription(task1.getTaskDescription())
                    .priority(task1.getPriority())
                    .taskStatus(task1.getStatus())
                    .deadline(task1.getDeadline())
                    .build();
            taskList.add(response);
        }

        return taskList;
    }

    @Override
    public void updateTask(TaskUpdateRequest taskRequest, String email, Long id) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));
        Task task = taskRepository.findById(id).orElseThrow(()-> new NotFoundException("Task not found"));

        task.setTaskName(taskRequest.getTaskName());
        task.setTaskDescription(taskRequest.getTaskDescription());
        task.setPriority(taskRequest.getPriority());
        task.setDeadline(taskRequest.getDeadline());
        task.setStatus(taskRequest.getStatus());

        taskRepository.save(task);

    }

}
