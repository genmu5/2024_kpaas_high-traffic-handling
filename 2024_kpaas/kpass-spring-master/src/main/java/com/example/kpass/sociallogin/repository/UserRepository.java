package com.example.kpass.sociallogin.repository;

import com.example.kpass.sociallogin.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUserId(String userId);
}