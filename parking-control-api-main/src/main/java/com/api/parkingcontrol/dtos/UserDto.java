package com.api.parkingcontrol.dtos;

import lombok.Data;
import net.bytebuddy.utility.RandomString;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
public class UserDto {


    private UUID userId;
    @NotBlank
    private String username;
    @NotBlank
    private final String password = RandomString.make(8);
    @Email
    private String email;
    private String name;
    private String phone;
    private boolean isLocked;
    private boolean isActive;
}
