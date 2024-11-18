package com.mathias.flexisaf.repository;

import com.mathias.flexisaf.entity.Task;
import com.mathias.flexisaf.enums.Priority;
import com.mathias.flexisaf.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserEmailAndDeleted(String email, boolean deleted);


    List<Task> findByUserEmailAndStatusAndDeletedFalse(String email, Status status);


    Optional<Task> findByTaskNameIgnoreCase(String taskName);

    List<Task> findByUserEmailAndPriorityAndDeletedFalse(String email, Priority priority);

    Long countByStatusAndDeletedFalse(Status status);

    @Query("SELECT t FROM Task t WHERE t.deleted = false")
    List<Task> findAllActiveTasks();

    @Query("SELECT t FROM Task t WHERE t.deleted = true AND t.deletedAt > :cutoffDate")
    List<Task> findRecoverableTasks(@Param("cutoffDate") LocalDateTime cutoffDate);
}


