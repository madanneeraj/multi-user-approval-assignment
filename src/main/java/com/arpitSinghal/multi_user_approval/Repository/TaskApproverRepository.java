package com.arpitSinghal.multi_user_approval.Repository;

import com.arpitSinghal.multi_user_approval.Entity.TaskApprover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskApproverRepository extends JpaRepository<TaskApprover, Long> {
    List<TaskApprover> findByTaskId(Long taskId);
    Optional<TaskApprover> findByTaskIdAndUserId(Long taskId, Long userId);
    long countByTaskIdAndApprovedTrue(Long taskId);
}
