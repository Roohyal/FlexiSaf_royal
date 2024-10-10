package com.mathias.flexisaf.repository;

import com.mathias.flexisaf.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
