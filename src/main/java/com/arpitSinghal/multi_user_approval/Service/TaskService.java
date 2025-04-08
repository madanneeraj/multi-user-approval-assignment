package com.arpitSinghal.multi_user_approval.Service;

import com.arpitSinghal.multi_user_approval.Entity.User;
import com.arpitSinghal.multi_user_approval.dto.TaskRequest;
import org.springframework.http.ResponseEntity;

public interface TaskService {
    ResponseEntity<?> createTask(TaskRequest req, User creator);
    ResponseEntity<?> approveTask(Long taskId, User approver);
    ResponseEntity<?> getNotifications(User user);
    ResponseEntity<?> addComment(Long taskId, String content, User user);
    ResponseEntity<?> getComments(Long taskId);

}
