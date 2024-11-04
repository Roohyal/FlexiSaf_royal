package com.mathias.flexisaf.infrastructure.controller;

import com.mathias.flexisaf.payload.request.TaskRequest;
import com.mathias.flexisaf.payload.request.TaskUpdateRequest;
import com.mathias.flexisaf.payload.response.TaskListResponse;
import com.mathias.flexisaf.payload.response.TaskResponse;
import com.mathias.flexisaf.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskRequest taskRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
     TaskResponse taskResponse = taskService.createTask(taskRequest, currentUsername);
        return ResponseEntity.ok(taskResponse);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteTask(@RequestParam Long taskId){
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("Task Deleted Successfully");
    }

    @GetMapping("/get-all-tasks")
    public ResponseEntity<?> getAllTasks(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
       List<TaskListResponse> taskListResponse = taskService.getAllTasks(currentUsername);
       return ResponseEntity.ok(taskListResponse);
    }

    @PutMapping("/update-task")
    public String updateTask(@RequestBody TaskUpdateRequest taskRequest, @RequestParam Long taskId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        taskService.updateTask(taskRequest,currentUsername,taskId);
        return "Task has been updated successfully";
    }
}
