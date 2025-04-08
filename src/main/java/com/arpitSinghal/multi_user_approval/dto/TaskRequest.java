package com.arpitSinghal.multi_user_approval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    public String title;
    public String description;
    public List<Long> approverIds;
}
