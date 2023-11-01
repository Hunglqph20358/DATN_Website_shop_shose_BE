package com.example.backend.core.security.controller;

import com.example.backend.core.security.config.custom.CustomUserDetailService;
import com.example.backend.core.security.config.custom.CustomUserDetails;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;


import java.time.Instant;


@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class SercurityController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserService usersService;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpRepquest signUpFormRequest){
        if(usersService.existsByUsername(signUpFormRequest.getUsername())){
            return new ResponseEntity<>(new MessageResponse("The Username is existed"), HttpStatus.OK);
        }
            Users users = Users.builder()
                    .code(signUpFormRequest.getCode())
                    .fullname(signUpFormRequest.getFullname())
                    .email(signUpFormRequest.getEmail())
                    .createDate(Instant.now())
                    .username(signUpFormRequest.getUsername())
                    .password(passwordEncoder.encode(signUpFormRequest.getPassword())).build();
            String strRoles = signUpFormRequest.getRole();
            String roles;
            try {
                if(strRoles.equalsIgnoreCase("ADMIN")){
                    roles = "ADMIN";
                    users.setId_customer(null);
                    users.setId_staff(Integer.valueOf(signUpFormRequest.getId_staff()));
                    users.setRole(roles);
                } else if (strRoles.equalsIgnoreCase("STAFF")) {
                    roles = "STAFF";
                    users.setId_customer(null);
                    users.setId_staff(Integer.valueOf(signUpFormRequest.getId_staff()));
                    users.setRole(roles);
                }else {
                    roles = "";
                    users.setId_staff(null);
                    users.setId_customer(Integer.valueOf(signUpFormRequest.getId_customer()));
                    users.setRole(roles);
                }
                usersService.saveOrUpdate(users);
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<>(new MessageResponse("Error occurred during registration"), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        return new ResponseEntity<>(new MessageResponse("Create Success"), HttpStatus.CREATED);
    }
    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@Valid @RequestBody SignInRequet signInRequet, HttpServletRequest request ){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequet.getUsername(), signInRequet.getPassword())
        );
        UsersDTO usersDTO = new UsersDTO();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(customUserDetails);
        return ResponseEntity.ok(new JwtResponse(token, usersDTO.toUserDTO(customUserDetails)));
    }
}
