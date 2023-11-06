package com.example.backend.core.security.controller;

import com.example.backend.core.security.config.custom.CustomUserDetailService;
import com.example.backend.core.security.config.custom.CustomUserDetails;
import com.example.backend.core.security.config.custom.CustomerUserDetalsService;
import com.example.backend.core.security.dto.UsersDTO;
import com.example.backend.core.security.dto.request.SignInRequet;
import com.example.backend.core.security.dto.request.SignUpRepquest;
import com.example.backend.core.security.dto.response.JwtResponse;
import com.example.backend.core.security.dto.response.MessageResponse;
import com.example.backend.core.security.entity.Users;
import com.example.backend.core.security.jwt.JwtEntryPoint;
import com.example.backend.core.security.jwt.JwtTokenProvider;
import com.example.backend.core.security.serivce.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.time.Instant;


@RestController
@RequestMapping("/admin/api")
@CrossOrigin("*")
public class SercurityController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserService usersService;
    @Autowired
    private CustomerUserDetalsService customUserDetailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpRepquest signUpFormRequest){
        if(usersService.existsByUsername(signUpFormRequest.getUsername())){
            return new ResponseEntity<>(new MessageResponse("The Username is existed"), HttpStatus.OK);
        }
        if(usersService.existsByEmail(signUpFormRequest.getEmail())){
            return new ResponseEntity<>(new MessageResponse("The email is existed"), HttpStatus.OK);
        }

            Users users = Users.builder()
                    .code("NV" + Instant.now().getEpochSecond())
                    .fullname(signUpFormRequest.getFullname())
                    .gender(signUpFormRequest.getGender())
                    .birthday(signUpFormRequest.getBirthday())
                    .phone(signUpFormRequest.getPhone())
                    .email(signUpFormRequest.getEmail())
                    .createDate(Instant.now())
                    .username(signUpFormRequest.getUsername())
                    .password(passwordEncoder.encode(signUpFormRequest.getPassword())).build();
            String strRoles = signUpFormRequest.getRole();
            String roles;
            try {
                if(strRoles.equalsIgnoreCase("ADMIN")){
                    roles = "ADMIN";
                } else if (strRoles.equalsIgnoreCase("STAFF")) {
                    roles = "STAFF";
                }else {
                    roles = "CUSTOMER";
                }
                users.setRole(roles);
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(new MessageResponse("Error occurred during registration"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            usersService.saveOrUpdate(users);
        return new ResponseEntity<>(new MessageResponse("Create Success"), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@Valid @RequestBody SignInRequet signInRequet, HttpServletRequest request ){
            String uri = request.getRequestURI();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequet.getUsername(),signInRequet.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(customUserDetails);
            String username = customUserDetails.getUsername();
            String role = customUserDetails.getRole();
            String code = customUserDetails.getCode();
            String phone = customUserDetails.getPhone();
            String email = customUserDetails.getEmail();
            return ResponseEntity.ok(new JwtResponse(token,username,role,code,phone,email));
    }
}
