package com.api.parkingcontrol.Responses;


import com.api.parkingcontrol.models.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private UUID userId;

    private String username;

    private String email;

    private String name;

    private String phone;

    private boolean isLocked;

    private boolean isActive;


    public UserResponse(UserModel userModel) {
        this.userId = userModel.getUserId();
        this.username = userModel.getUsername();
        this.email = userModel.getEmail();
        this.name = userModel.getName();
        this.phone = userModel.getPhone();
        this.isActive = userModel.isActive();

    }
}
