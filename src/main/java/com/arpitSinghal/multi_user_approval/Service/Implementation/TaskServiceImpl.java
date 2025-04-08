package com.arpitSinghal.multi_user_approval.Service.Implementation;

import com.arpitSinghal.multi_user_approval.Entity.Comment;
import com.arpitSinghal.multi_user_approval.Entity.Task;
import com.arpitSinghal.multi_user_approval.Entity.TaskApprover;
import com.arpitSinghal.multi_user_approval.Entity.User;
import com.arpitSinghal.multi_user_approval.Repository.CommentRepository;
import com.arpitSinghal.multi_user_approval.Repository.TaskApproverRepository;
import com.arpitSinghal.multi_user_approval.Repository.TaskRepository;
import com.arpitSinghal.multi_user_approval.Repository.UserRepository;
import com.arpitSinghal.multi_user_approval.Service.TaskService;
import com.arpitSinghal.multi_user_approval.dto.TaskRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepo;
    private TaskApproverRepository approverRepo;
    private UserRepository userRepo;
    private CommentRepository commentRepo;

    public TaskServiceImpl(TaskRepository taskRepo, TaskApproverRepository approverRepo, UserRepository userRepo, CommentRepository commentRepo) {
        this.taskRepo = taskRepo;
        this.approverRepo = approverRepo;
        this.userRepo = userRepo;
        this.commentRepo = commentRepo;
    }

    public ResponseEntity<?> createTask(TaskRequest req, User creator) {
        Task task = new Task();
        task.setTitle(req.title);
        task.setDescription(req.description);
        task.setCreatedBy(creator);
        task.setStatus("PENDING");
        taskRepo.save(task);

        for (Long approverId : req.approverIds) {
            User approver = userRepo.findById(approverId).orElseThrow();
            TaskApprover ta = new TaskApprover();
            ta.setTask(task);
            ta.setUser(approver);
            approverRepo.save(ta);
        }
        return ResponseEntity.ok(Map.of("taskId", task.getId(), "message", "Task created Successfully"));
    }

    public ResponseEntity<?> approveTask(Long taskId, User approver) {
        TaskApprover ta = approverRepo.findByTaskIdAndUserId(taskId, approver.getId())
                .orElseThrow(() -> new RuntimeException("Not an assigned approver"));
        if (ta.isApproved()) return ResponseEntity.badRequest().body("Already approved");
        ta.setApproved(true);
        ta.setApprovedOn(LocalDateTime.now());
        approverRepo.save(ta);

        long count = approverRepo.countByTaskIdAndApprovedTrue(taskId);
        if (count >= 3) {
            Task task = taskRepo.findById(taskId).orElseThrow();
            task.setStatus("APPROVED");
            taskRepo.save(task);
        }

        return ResponseEntity.ok("Approval recorded");
    }

    public ResponseEntity<?> getNotifications(User user) {
        List<Task> createdTasks = taskRepo.findAll().stream()
                .filter(task -> task.getCreatedBy().getId().equals(user.getId()))
                .collect(Collectors.toList());

        List<String> notifications = new ArrayList<>();
        for (Task task : createdTasks) {
            List<TaskApprover> approvers = approverRepo.findByTaskId(task.getId());
            for (TaskApprover ta : approvers) {
                if (ta.isApproved()) {
                    notifications.add(ta.getUser().getName() + " approved task " + task.getTitle());
                }
            }
        }
        return ResponseEntity.ok(notifications);
    }

    @Override
    public ResponseEntity<?> addComment(Long taskId, String content, User user) {
        Task task = taskRepo.findById(taskId).orElseThrow();
        Comment comment = new Comment();
        comment.setTask(task);
        comment.setUser(user);
        comment.setComment(content);
        commentRepo.save(comment);
        return ResponseEntity.ok("Comment added");
    }

    @Override
    public ResponseEntity<?> getComments(Long taskId) {
        List<Comment> comments = commentRepo.findByTaskId(taskId);
        return ResponseEntity.ok(comments);
    }
}
