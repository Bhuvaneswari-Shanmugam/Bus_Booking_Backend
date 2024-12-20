package com.app.bookingsystem.controller;

import com.app.bookingsystem.dto.ResponseDTO;
import com.app.bookingsystem.dto.SigninDTO;
import com.app.bookingsystem.dto.SignupDTO;
import com.app.bookingsystem.dto.UserUpdateDTO;
import com.app.bookingsystem.service.UserCredentialService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class UserCredentialController {

    private final UserCredentialService usersCredentialService;

    public UserCredentialController(final UserCredentialService usersCredentialService){
        this.usersCredentialService=usersCredentialService;
    }

    @PostMapping("/sign-up")
    public ResponseDTO signUp(@RequestBody final SignupDTO signupDTO){
        return this.usersCredentialService.signUp(signupDTO);
    }

    @PostMapping("/sign-in")
    public ResponseDTO signIn(@RequestBody final SigninDTO signinDto){
        return this.usersCredentialService.signIn(signinDto);
    }

    @GetMapping("/retrieve-user-detail/{id}")
    public ResponseDTO retrieveUserDetailById(@PathVariable final String id){
        return this.usersCredentialService.retrieveUserDetailById(id);
    }

    @PutMapping("/update-user/{id}")
    public ResponseDTO updateUser(@PathVariable final String id, @RequestBody final UserUpdateDTO userUpdateDTO) {
        return this.usersCredentialService.updateUserDetails(id, userUpdateDTO);
    }
    @GetMapping("/retrieve-all-user")
    public ResponseDTO getAllUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return this.usersCredentialService.retrieveAllUser(page, size);
    }
}
