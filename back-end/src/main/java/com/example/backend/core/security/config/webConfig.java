package com.example.backend.core.security.config;

import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.security.config.custom.CustomUserDetailService;
import com.example.backend.core.security.jwt.JwtAuthenticationFillter;
import com.example.backend.core.security.jwt.JwtEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class webConfig {
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtAuthenticationFillter jwtAuthenticationFilter(){
        return new JwtAuthenticationFillter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager()throws Exception{
        return new ProviderManager(Arrays.asList(daoAuthenticationProvider()));
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(c -> c.disable()).csrf(cf -> cf.disable());
        http.authorizeHttpRequests(author -> {
            try {
                author.requestMatchers("/sign-in").permitAll()
                        .requestMatchers("/sign-up").permitAll()
                        .requestMatchers(AppConstant.API_ADMIN).hasAnyAuthority("ADMIN")
                        .requestMatchers(AppConstant.API_STAFF).hasAnyAuthority("STAFF","ADMIN")
                        .requestMatchers(AppConstant.API_VIEW).hasAnyAuthority("STAFF","ADMIN","CUSTOMER")
                        .and().exceptionHandling()
                        .authenticationEntryPoint(jwtEntryPoint).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
