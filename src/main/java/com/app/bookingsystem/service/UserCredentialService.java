package com.app.bookingsystem.service;

import com.app.bookingsystem.config.TokenProvider;
import com.app.bookingsystem.dto.*;
import com.app.bookingsystem.entity.UserCredential;
import com.app.bookingsystem.repository.UserCredentialRepository;
import com.app.bookingsystem.util.Constants;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class UserCredentialService implements UserDetailsService {

    private final UserCredentialRepository userCredentialRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Lazy
    public UserCredentialService(UserCredentialRepository userCredentialRepository, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.userCredentialRepository = userCredentialRepository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    public ResponseDTO signUp(final SignupDTO signUpDTO) {
        final UserCredential userCredential = UserCredential.builder()
                .firstName(signUpDTO.getFirstName())
                .lastName(signUpDTO.getLastName())
                .email(signUpDTO.getEmail())
                .password(new BCryptPasswordEncoder().encode(signUpDTO.getPassword()))
                .role(signUpDTO.getRole())
                .termsAccepted(signUpDTO.getTermsAccepted())
                .build();
        return ResponseDTO.builder()
                .message(Constants.CREATED)
                .data(this.userCredentialRepository.save(userCredential))
                .statusCode(200)
                .build();
    }

    public ResponseDTO signIn(final SigninDTO signInDTO) throws AuthenticationException {
         final UserCredential user = this.userCredentialRepository.findByEmail(signInDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email doesn't exist, so please sign up"));
         if(user!=null){
             final var userNamePassword = new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword());
             final var authorizedUser = this.authenticationManager.authenticate(userNamePassword);
             final var accessToken = tokenProvider.generateAccessToken((UserCredential) authorizedUser.getPrincipal());
             final var refreshToken = tokenProvider.generateRefreshToken((UserCredential) authorizedUser.getPrincipal());

             return ResponseDTO.builder()
                     .message(Constants.RETRIEVED)
                     .data(new JwtDTO(accessToken, refreshToken))
                     .statusCode(200)
                     .build();
         }
        return ResponseDTO.builder()
                .message("can't signin .... please signup before signin")
                .statusCode(200)
                .build();
    }

    public ResponseDTO refreshAccessToken(final RefreshTokenDTO requestDTO) {
        try {
            String newAccessToken = tokenProvider.refreshAccessToken(requestDTO.getRefreshToken());
            String refreshToken = requestDTO.setRefreshToken(newAccessToken);
            return ResponseDTO.builder().message(Constants.CREATED).data(refreshToken).statusCode(200).build();
        } catch (Exception e) {
            return ResponseDTO.builder().message(Constants.NOT_FOUND).data("Invalid refresh token").statusCode(401).build();
        }
    }

    @Override
    public UserDetails loadUserByUsername(final String userName) {
        return userCredentialRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("user id doesn't exist"));
    }

    public boolean emailValidation(final String email) {
        if (email == null) {
            return false;
        }
        return Pattern.compile("^[a-z0-9+_.-]+@(gmail|yahoo|outlook|zoho)\\.com$").matcher(email).matches();
    }


    private boolean passwordValidation(final String password) {
       final  String pass = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        return Pattern.compile(pass).matcher(password).matches();
    }


    public ResponseDTO retrieveUserDetailById(final String id) {
        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(this.userCredentialRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("USER ID NOT EXIST")))
                .statusCode(200)
                .build();
    }


    public ResponseDTO getUserDetail(final String id) {
        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(this.userCredentialRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("USER ID NOT EXIST")))
                .statusCode(200)
                .build();
    }

    public ResponseDTO updateUserDetails(final String id, final UserUpdateDTO userUpdateDTO) {
        final UserCredential user = userCredentialRepository.findById(id).orElse(null);
        if (user == null) {
            return new ResponseDTO("User not found", null, 404);
        }
        if (userUpdateDTO.getFirstName() != null && !userUpdateDTO.getFirstName().isEmpty()) {
            user.setFirstName(userUpdateDTO.getFirstName());
        }
        if (userUpdateDTO.getLastName() != null && !userUpdateDTO.getLastName().isEmpty()) {
            user.setLastName(userUpdateDTO.getLastName());
        }
        if (userUpdateDTO.getEmail() != null && !userUpdateDTO.getEmail().isEmpty()) {
            user.setEmail(userUpdateDTO.getEmail());
        }
        return ResponseDTO.builder()
                .message(Constants.UPDATED)
                .data( userCredentialRepository.save(user))
                .statusCode(200)
                .build();
    }

    public ResponseDTO retrieveAllUser(int page, int size) {
        Page<UserCredential> userPage = userCredentialRepository.findAll(PageRequest.of(page, size));
        return ResponseDTO.builder()
                .message(Constants.RETRIEVED)
                .data(userPage.getContent())
                .statusCode(200)
                .build();
    }
}
