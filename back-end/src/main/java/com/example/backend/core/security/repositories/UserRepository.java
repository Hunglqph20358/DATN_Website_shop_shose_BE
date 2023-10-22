package com.example.backend.core.security.repositories;

import com.example.backend.core.security.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    public Users findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByPhone(String phone);
    String findByRole(String role);
}
