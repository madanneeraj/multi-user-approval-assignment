package com.arpitSinghal.multi_user_approval.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    public String name;
    public String email;
    public String password;
}
