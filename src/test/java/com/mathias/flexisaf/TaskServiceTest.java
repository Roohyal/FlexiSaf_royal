package com.mathias.flexisaf;

import com.mathias.flexisaf.entity.Person;
import com.mathias.flexisaf.entity.Task;
import com.mathias.flexisaf.enums.Priority;
import com.mathias.flexisaf.enums.Status;
import com.mathias.flexisaf.exceptions.NotFoundException;
import com.mathias.flexisaf.payload.request.TaskRequest;
import com.mathias.flexisaf.payload.response.TaskResponse;
import com.mathias.flexisaf.repository.PersonRepository;
import com.mathias.flexisaf.repository.TaskRepository;
import com.mathias.flexisaf.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private TaskServiceImpl taskService;


    @Test
    void testCreateTaskSuccess(){
        // Arrange: Setup mock data
        String email = "test@example.com";
        TaskRequest taskRequest = new TaskRequest(
                "Task Name",
                "Task Description",
                Status.PENDING,
                Priority.HIGH,
                LocalDate.now()
        );

        Person mockPerson = new Person();
        mockPerson.setEmail(email);


        Task mockTask = Task.builder()
                .taskName("Task Name")
                .taskDescription("Task Description")
                .priority(Priority.HIGH)
                .status(Status.PENDING)
                .deadline(taskRequest.getDeadline())
                .user(mockPerson)
                .build();

        when(personRepository.findByEmail(email)).thenReturn(Optional.of(mockPerson));
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);


        // Act: Call the method under test
        TaskResponse response = taskService.createTask(taskRequest, email);

        // Assert: Validate the response and behavior
        assertEquals("003", response.getResponseCode());
        assertEquals("Task created", response.getResponseMessage());

       // Verify interactions
        verify(personRepository, times(1)).findByEmail(email);
        verify(taskRepository, times(1)).save(any(Task.class));
    }


    @Test
    void testCreateTaskFailure(){
        String email = "test@example.com";
        TaskRequest taskRequest = new TaskRequest(
                "Task Name",
                "Task Description",
                Status.PENDING,
                Priority.HIGH,
                LocalDate.now()
        );

        when(personRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act and Assert: Expect a NotFoundException
        assertThrows(NotFoundException.class, () -> {
            taskService.createTask(taskRequest, email);
        });

        // Verify that save was never called
        verify(taskRepository, never()).save(any(Task.class));
    }


}
