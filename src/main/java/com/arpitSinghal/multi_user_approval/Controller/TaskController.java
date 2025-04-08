package com.arpitSinghal.multi_user_approval.Controller;

import com.arpitSinghal.multi_user_approval.Entity.User;
import com.arpitSinghal.multi_user_approval.Service.TaskService;
import com.arpitSinghal.multi_user_approval.dto.TaskRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskRequest req, @RequestHeader("userId") Long userId) {
        User user = new User();
        user.setId(userId);
        return taskService.createTask(req, user);
    }

    @PostMapping("/{taskId}/approve")
    public ResponseEntity<?> approveTask(@PathVariable Long taskId, @RequestHeader("userId") Long userId) {
        User user = new User(); user.setId(userId);
        return taskService.approveTask(taskId, user);
    }

    @GetMapping("/notifications")
    public ResponseEntity<?> getNotifications(@RequestHeader("userId") Long userId) {
        User user = new User();
        user.setId(userId);
        return taskService.getNotifications(user);
    }
    @PostMapping("/{taskId}/comments")
    public ResponseEntity<?> addComment(@PathVariable Long taskId, @RequestBody Map<String, Object> req, @RequestHeader("userId") Long userId) {
        User user = new User(); user.setId(userId);
        return taskService.addComment(taskId, (String) req.get("content"), user);
    }

    @GetMapping("/{taskId}/comments")
    public ResponseEntity<?> getComments(@PathVariable Long taskId) {
        return taskService.getComments(taskId);
    }
}
