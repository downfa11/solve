package com.ns.solve.Domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="membership")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Membership {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Long membershipId;

    private String name;
    private String nickname;
    private String address;
    private String email;

    private String account;
    private String password;

    public enum ROLE{
        ADMIN, MANAGER, USER
    }
    private ROLE role;

    private String refreshToken;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Column(name = "created_at")
    private Timestamp createdAt;


    public String getRoleName() {
        return "ROLE_" + this.role.name();
    }

}