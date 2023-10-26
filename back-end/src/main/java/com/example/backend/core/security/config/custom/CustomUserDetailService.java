package com.example.backend.core.security.config.custom;

import com.example.backend.core.security.entity.Users;
import com.example.backend.core.security.repositories.CustomerSPRepository;
import com.example.backend.core.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private CustomerSPRepository customerSPRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Users user = repository.findByUsername(username);
            if (user==null){
                throw  new UsernameNotFoundException("Khong Tim Thay User");
            }
            return CustomUserDetails.mapUserToUserDetail(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
