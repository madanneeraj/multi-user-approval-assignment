package com.arpitSinghal.multi_user_approval.Repository;

import com.arpitSinghal.multi_user_approval.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
