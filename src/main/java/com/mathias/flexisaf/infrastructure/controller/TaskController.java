package com.mathias.flexisaf.infrastructure.controller;

import com.mathias.flexisaf.payload.request.TaskRequest;
import com.mathias.flexisaf.payload.response.TaskResponse;
import com.mathias.flexisaf.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskRequest taskRequest){
        TaskResponse taskResponse = taskService.createTask(taskRequest);
        return ResponseEntity.ok(taskResponse);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteTask(@RequestParam Long taskId){
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("Task Deleted Successfully");
    }
}
