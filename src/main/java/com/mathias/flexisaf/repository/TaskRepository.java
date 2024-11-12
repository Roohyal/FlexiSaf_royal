package com.mathias.flexisaf.repository;

import com.mathias.flexisaf.entity.Task;
import com.mathias.flexisaf.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserEmail(String email);

    List<Task> findByUserEmailAndStatus(String email, Status status);


    Optional<Task> findByTaskName(String taskName);
}
