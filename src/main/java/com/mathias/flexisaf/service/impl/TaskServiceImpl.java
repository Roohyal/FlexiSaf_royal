package com.mathias.flexisaf.service.impl;

import com.mathias.flexisaf.entity.Person;
import com.mathias.flexisaf.entity.Task;
import com.mathias.flexisaf.enums.Priority;
import com.mathias.flexisaf.enums.Status;
import com.mathias.flexisaf.exceptions.NotFoundException;
import com.mathias.flexisaf.payload.request.TaskRequest;
import com.mathias.flexisaf.payload.request.TaskUpdateRequest;
import com.mathias.flexisaf.payload.response.TaskListResponse;
import com.mathias.flexisaf.payload.response.TaskResponse;
import com.mathias.flexisaf.payload.response.TaskStatusSummary;
import com.mathias.flexisaf.repository.PersonRepository;
import com.mathias.flexisaf.repository.TaskRepository;
import com.mathias.flexisaf.service.TaskService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
                .taskName(cleanInput(taskRequest.getTaskName()))
                .taskDescription(cleanInput(taskRequest.getTaskDescription()))
                .priority(taskRequest.getPriority())
                .status(Status.PENDING)
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
    public void softdeleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setDeleted(true);
        task.setDeletedAt(LocalDateTime.now());
        taskRepository.save(task);

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

        task.setTaskName(cleanInput(taskRequest.getTaskName()));
        task.setTaskDescription(cleanInput(taskRequest.getTaskDescription()));
        task.setPriority(taskRequest.getPriority());
        task.setDeadline(taskRequest.getDeadline());
        task.setStatus(taskRequest.getStatus());

        taskRepository.save(task);

    }

    @Override
    public List<TaskListResponse> getCurrentUserTasks(String email) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));

        List<Task> tasks = taskRepository.findByUserEmailAndDeleted(email, false);


        // Use the builder pattern for each Task and collect as TaskListResponse
        return tasks.stream()
                .map(task -> TaskListResponse.builder()
                        .taskName(task.getTaskName())          // Assuming TaskListResponse has an ID field// Set other relevant fields as needed
                        .taskDescription(task.getTaskDescription())
                        .taskStatus(task.getStatus())
                        .priority(task.getPriority())
                        .deadline(task.getDeadline())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskListResponse> getTasksByCurrentUserAndStatus(String email, Status status) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));

        List<Task> tasks = taskRepository.findByUserEmailAndStatusAndDeletedFalse(email, status);

        return tasks.stream()
                .map(task -> TaskListResponse.builder()
                        .taskName(task.getTaskName())          // Assuming TaskListResponse has an ID field// Set other relevant fields as needed
                        .taskDescription(task.getTaskDescription())
                        .taskStatus(task.getStatus())
                        .priority(task.getPriority())
                        .deadline(task.getDeadline())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskListResponse> getCompletedTasksForCurrentUser(String email) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));

        List<Task> tasks = taskRepository.findByUserEmailAndStatusAndDeletedFalse(email, Status.COMPLETED);

        return tasks.stream()
                .map(task -> TaskListResponse.builder()
                        .taskName(task.getTaskName())
                        .taskDescription(task.getTaskDescription())
                        .taskStatus(task.getStatus())
                        .priority(task.getPriority())
                        .deadline(task.getDeadline())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public Task updateTaskStatus(Long taskId, Status status, String email) {

        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));

        Task task = taskRepository.findById(taskId).orElseThrow(()-> new NotFoundException("Task not found"));


        task.setStatus(status);

        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long taskId, String title, String email) {
        Task task;

        if (taskId != null) {
            task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new NotFoundException("Task not found with id " + taskId));
        } else if (title != null && !title.isEmpty()) {
            task = taskRepository.findByTaskNameIgnoreCase(title)
                    .orElseThrow(() -> new NotFoundException("Task not found with title " + title));
        } else {
            throw new IllegalArgumentException("Both id and title are null");
        }

        // Check if the task is deleted
        if (task.isDeleted()) {
            throw new NotFoundException("Task with " +
                    (taskId != null ? "id " + taskId : "title '" + title + "'") +
                    " has been deleted and cannot be found.");
        }

        return task;
    }


    @Override
    public List<TaskListResponse> getTasksByCurrentUserandPriority(String email, Priority priority) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));

        List<Task> tasks = taskRepository.findByUserEmailAndPriorityAndDeletedFalse(email, priority);

        return tasks.stream()
                .map(task -> TaskListResponse.builder()
                        .taskName(task.getTaskName())
                        .taskDescription(task.getTaskDescription())
                        .taskStatus(task.getStatus())
                        .priority(task.getPriority())
                        .deadline(task.getDeadline())
                        .build()
                )
                .collect(Collectors.toList());


    }

    @Override
    public List<TaskStatusSummary> getTaskStatusSummary(String email) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));

        Long pending = taskRepository.countByStatusAndDeletedFalse(Status.PENDING);
        Long completed = taskRepository.countByStatusAndDeletedFalse(Status.COMPLETED);
        Long inProgress = taskRepository.countByStatusAndDeletedFalse(Status.IN_PROGRESS);

        Long total = pending + completed + inProgress;


        TaskStatusSummary taskStatusSummary = TaskStatusSummary.builder()
                .completed(completed)
                .inProgress(inProgress)
                .pending(pending)
                .totalTasks(total)
                .build();

        return Collections.singletonList(taskStatusSummary);
    }

    @Override
    public void recoverTask(Long taskId, String email) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));

        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found with id " + taskId));

        if (task.isDeleted() && task.getDeletedAt().isAfter(LocalDateTime.now().minusDays(30)) ) {
            task.setDeleted(false);
            task.setDeletedAt(null);
            taskRepository.save(task);
        } else {
            throw new RuntimeException("Task cannot be recovered");
        }

    }

    @Override
    public List<Task> getRecoverableTasks(String email) {
        personRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found"));

        LocalDateTime cutOffDate = LocalDateTime.now().minusDays(30);

        return taskRepository.findRecoverableTasks(cutOffDate);
    }


    // Helper method to trim and remove extra spaces
    private String cleanInput(String input) {
        return input == null ? null : input.trim().replaceAll("\\s+", " ");
    }

}
