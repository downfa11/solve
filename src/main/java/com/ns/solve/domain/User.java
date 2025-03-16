package com.ns.solve.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String account;
    private String password;

    private Long score; // 맞춘 문제 개수 (전체 랭킹, 분야별 랭킹)

    private LocalDateTime created;
    private LocalDateTime lastActived;
}
