package com.ns.solve.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemId;

    public enum ProblemStatus{
        ALGORITHM, ASSIGNMENT, CTF
    }

    private ProblemStatus status;

    private Long membershipId;
    private LocalDateTime deadline;
    private String detail;


    private List<Case> caseList;


}
