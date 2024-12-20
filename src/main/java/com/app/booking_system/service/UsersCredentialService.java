package com.app.booking_system.service;

import com.app.booking_system.config.TokenProvider;
import com.app.booking_system.dto.*;
import com.app.booking_system.entity.Admin;
import com.app.booking_system.entity.Customer;
import com.app.booking_system.entity.UserCredential;
import com.app.booking_system.repository.AdminRepository;
import com.app.booking_system.repository.CustomerRepository;
import com.app.booking_system.repository.UserCredentialRepository;
import com.app.booking_system.util.Constants;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;


@Service
public class UsersCredentialService implements  UserDetailsService {

    private final UserCredentialRepository userCredentialRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;



    @Lazy
    public UsersCredentialService(UserCredentialRepository userCredentialRepository , AuthenticationManager authenticationManager , TokenProvider tokenProvider , CustomerRepository customerRepository, AdminRepository adminRepository, PasswordEncoder passwordEncoder){
        this.userCredentialRepository=userCredentialRepository;
        this.authenticationManager=authenticationManager;
        this.tokenProvider=tokenProvider;
        this.customerRepository=customerRepository;
        this.adminRepository=adminRepository;
        this.passwordEncoder=passwordEncoder;
    }



    public ResponseDTO signUp(SignupDTO signUp) {

        String encodedPassword = new BCryptPasswordEncoder().encode(signUp.getPassword());

        UserCredential userCredential=UserCredential.builder()
                .firstName(signUp.getFirstName())
                .lastName(signUp.getLastName())
                .dateOfBirth(signUp.getDateOfBirth())
                .email(signUp.getEmail())
                .password(encodedPassword)
                .gender(signUp.getGender())
                .role(signUp.getRole())
                .contactNumber(signUp.getContactNumber())
                .address(signUp.getAddress())
                .termsAccepted(signUp.getTermsAccepted())
                .build();
        this.userCredentialRepository.save(userCredential);


//        if (signUp.getRole().equals("ADMIN")) {
//            Admin admin = Admin.builder()
//                    .firstName(signUp.getFirstName())
//                    .lastName(signUp.getLastName())
//                    .dateOfBirth(signUp.getDateOfBirth())
//                    .email(signUp.getEmail())
//                    .password(encodedPassword)
//                    .gender(signUp.getGender())
//                    .contactNumber(signUp.getContactNumber())
//                    .address(signUp.getAddress())
//                    .termsAccepted(signUp.getTermsAccepted())
//                    .build();
//            return ResponseDTO.builder().statusCode(200).data(this.adminRepository.save(admin)).message(Constants.CREATED).build();
//
//        } else if (signUp.getRole().equals("CUSTOMER")) {
//            Customer customer = Customer.builder()
//                    .firstName(signUp.getFirstName())
//                    .lastName(signUp.getLastName())
//                    .dateOfBirth(signUp.getDateOfBirth())
//                    .email(signUp.getEmail())
//                    .password(encodedPassword)
//                    .gender(signUp.getGender())
//                    .contactNumber(signUp.getContactNumber())
//                    .address(signUp.getAddress())
//                    .termsAccepted(signUp.getTermsAccepted())
//                    .build();
//            return ResponseDTO.builder().statusCode(200).data(this.customerRepository.save(customer)).message(Constants.CREATED).build();
//        }
        return ResponseDTO.builder().statusCode(200).message(Constants.NOT_FOUND).build();
    }


    public ResponseDTO signIn(SigninDTO signIn) throws AuthenticationException {
        UserDetails user = userCredentialRepository.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email doesn't exist, so please sign up"));

        var userNamePassword = new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword());
        var authorizedUser = authenticationManager.authenticate(userNamePassword);
        var accessToken = tokenProvider.generateAccessToken((UserCredential) authorizedUser.getPrincipal());
        var refreshToken = tokenProvider.generateRefreshToken((UserCredential) authorizedUser.getPrincipal());

        String userId = tokenProvider.getUserIdFromToken(accessToken);

        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(new JwtDto(accessToken, refreshToken))
                .statusCode(200)
                .build();
    }





    public ResponseDTO refreshAccessToken(RefreshTokenDTO request) {
        try {
            String newAccessToken = tokenProvider.refreshAccessToken(request.getRefreshToken());
            String refreshToken = request.setRefreshToken(newAccessToken);
            return ResponseDTO.builder().message(Constants.CREATED).data(refreshToken).statusCode(200).build();
        } catch (Exception e) {

            return ResponseDTO.builder().message(Constants.NOT_FOUND).data("Invalid refresh token").statusCode(401).build();
        }
    }


    @Override
    public UserDetails loadUserByUsername(String userName) {
        UserCredential user=userCredentialRepository.findByEmail(userName)
                .orElseThrow(()-> new UsernameNotFoundException("user id doesn't exist"));
        return user;

    }


    public boolean emailValidation(String email) {

        if (email == null) {
            return false;
        }
        return Pattern.compile("^[a-z0-9+_.-]+@(gmail|yahoo|outlook|zoho)\\.com$").matcher(email).matches();
    }


    private boolean passwordValidation(String password) {
        String pass = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        return Pattern.compile(pass).matcher(password).matches();
    }


    public ResponseDTO getAllUserDetail(String id) {
        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(this.userCredentialRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("USER ID NOT EXIST")))
                .statusCode(200)
                .build();
    }


    public ResponseDTO getUserDetail(String id) {
        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(this.userCredentialRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("USER ID NOT EXIST")))
                .statusCode(200)
                .build();
    }


    public ResponseDTO updateUserDetails(String id, UserUpdateDTO userUpdateDTO) {
        UserCredential user = userCredentialRepository.findById(id).orElse(null);

        if (user == null) {
            return new ResponseDTO("User not found", null, 404);
        }

        if (userUpdateDTO.getEmail() != null) user.setEmail(userUpdateDTO.getEmail());
        if (userUpdateDTO.getAddress() != null) user.setAddress(userUpdateDTO.getAddress());
        if (userUpdateDTO.getContactNumber() != null) user.setContactNumber(userUpdateDTO.getContactNumber());
        if (userUpdateDTO.getGender() != null) user.setGender(userUpdateDTO.getGender());
        if (userUpdateDTO.getDateOfBirth() != null) user.setDateOfBirth(userUpdateDTO.getDateOfBirth());

        userCredentialRepository.save(user);
        return new ResponseDTO("User details updated successfully", user, 200);
    }


}
