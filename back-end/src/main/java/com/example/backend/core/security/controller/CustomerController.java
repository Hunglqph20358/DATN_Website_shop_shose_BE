package com.example.backend.core.security.controller;

import com.example.backend.core.model.Customer;
import com.example.backend.core.security.config.custom.CustomerUserDetailService;
import com.example.backend.core.security.config.custom.CustomerUserDetails;
import com.example.backend.core.security.dto.request.SignInRequet;
import com.example.backend.core.security.dto.request.SignUpRepquest;
import com.example.backend.core.security.dto.response.JwtResponse;
import com.example.backend.core.security.dto.response.MessageResponse;
import com.example.backend.core.security.jwt.JwtEntryPoint;
import com.example.backend.core.security.jwt.JwtTokenProvider;
import com.example.backend.core.security.serivce.CustomerSPService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/view/api")
public class CustomerController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    CustomerSPService customerSPService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtEntryPoint  jwtEntryPoint;

    @Autowired
    private CustomerUserDetailService customerUserDetailService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUpCustomer(@Valid @RequestBody SignUpRepquest signUpFormRequest){
        Customer customer = Customer.builder()
                .code(signUpFormRequest.getCode())
                .fullname(signUpFormRequest.getFullname())
                .gender(signUpFormRequest.getGender())
                .phone(signUpFormRequest.getPhone())
                .email(signUpFormRequest.getEmail())
                .createDate(Instant.now())
                .username(signUpFormRequest.getUsername())
                .password(passwordEncoder.encode(signUpFormRequest.getPassword())).build();
        String birthday = signUpFormRequest.getBirthday();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(birthday,formatter);
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        customer.setBirthday(instant);
        customerSPService.saveCustomer(customer);
        return new ResponseEntity<>(new MessageResponse("Create Success"), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> loginCus(@Valid @RequestBody SignInRequet signInRequet, HttpServletRequest request){
        String uri = request.getRequestURI();
        if(uri.contains("view")){
                UserDetails userDetails = customerUserDetailService.loadUserByUsername(signInRequet.getUsername());
                if(userDetails != null){
                    // Neu nguoi dung hop le set thong tin cho security context
                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(userDetails, signInRequet.getPassword(), userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    CustomerUserDetails customerUserDetails = (CustomerUserDetails) authentication.getPrincipal();
                    String token = jwtTokenProvider.generateTokenCustomer(customerUserDetails);
                    String username = customerUserDetails.getUsername();
                    String role = null;
                    String code = customerUserDetails.getCode();
                    String phone = customerUserDetails.getPhone();
                    String email = customerUserDetails.getEmail();
                    return ResponseEntity.ok(new JwtResponse(token,username,role,code,phone,email));
                }
        }else{
            return ResponseEntity.ok(new MessageResponse("Bạn không có quyền truy cập"));
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
