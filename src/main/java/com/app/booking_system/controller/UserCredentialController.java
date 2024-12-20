package com.app.booking_system.controller;

import com.app.booking_system.dto.ResponseDTO;
import com.app.booking_system.dto.SigninDTO;
import com.app.booking_system.dto.SignupDTO;
import com.app.booking_system.dto.UserUpdateDTO;
import com.app.booking_system.service.UsersCredentialService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class UserCredentialController {

    private final UsersCredentialService usersCredentialService;

    public UserCredentialController(UsersCredentialService usersCredentialService){
        this.usersCredentialService=usersCredentialService;
    }

    @PostMapping("/sign-up")
    public ResponseDTO signUp(@RequestBody SignupDTO signupDto){
        return this.usersCredentialService.signUp(signupDto);
    }

    @PostMapping("/sign-in")
    public ResponseDTO signIn(@RequestBody SigninDTO signinDto){
        return this.usersCredentialService.signIn(signinDto);
    }

    @GetMapping("/get-user-detail/{id}")
    public ResponseDTO getUserDetail(@PathVariable String id){
        return this.usersCredentialService.getAllUserDetail(id);
    }

    @PutMapping("/update-user/{id}")
    public ResponseDTO updateUser(@PathVariable String id, @RequestBody UserUpdateDTO userUpdateDTO) {
        return this.usersCredentialService.updateUserDetails(id, userUpdateDTO);
    }
}
