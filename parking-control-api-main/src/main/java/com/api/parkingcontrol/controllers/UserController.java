package com.api.parkingcontrol.controllers;


import com.api.parkingcontrol.Responses.UserResponse;
import com.api.parkingcontrol.dtos.UserDto;
import com.api.parkingcontrol.models.UserModel;
import com.api.parkingcontrol.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class UserController {


    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserDto userDto){
          return userService.createUser(userDto);
    }

    @GetMapping
    ResponseEntity<UserResponse> getUserById(@RequestParam UUID id)throws Exception{
        return userService.getUserById(id);
    }
}
