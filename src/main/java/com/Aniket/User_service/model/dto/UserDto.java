package com.Aniket.User_service.model.dto;

import com.Aniket.User_service.model.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userId;   // Optional: in case you need to reference user later
    private String email;    // used for authentication
    private String username; // if you want to show/display
    private Roles role;
}
