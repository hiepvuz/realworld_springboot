package com.example.demo2.repository;

import java.util.Map;
import java.util.Optional;

import com.example.demo2.model.profile.dto.ProfileDTOResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo2.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String userName);
}
