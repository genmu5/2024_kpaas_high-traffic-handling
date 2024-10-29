//package com.example.kpass.sociallogin.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.time.LocalDateTime;
//
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity(name = "users") //jakarta.persistence
//@Table(name = "users")
//@EntityListeners(AuditingEntityListener.class)
//public class UserEntity {
//    @Id
//    @Column(name = "user_id")
//    private String userId;
//    @Column(name = "user_name")
//    private String userName;
//    @Column(name = "user_email")
//    private String userEmail;
//    @Column(name = "user_login_type")
//    private String userLoginType;
//    @Column(name = "user_role")
//    private String userRole;
//    @Column(name = "created_at")
//    @CreatedDate
//    private LocalDateTime createdAt;
//
//    public UserEntity(String userId, String userName, String userEmail, String userLoginType, String userRole) {
//        this.userId = userId;
//        this.userName = userName;
//        this.userEmail = userEmail;
//        this.userLoginType = userLoginType;
//        this.userRole = userRole;
//    }
//}