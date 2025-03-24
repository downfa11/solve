package com.ns.solve.domain;

import com.ns.solve.domain.problem.Problem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Solved {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User solvedUser;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem solvedProblem;

    private LocalDateTime solvedTime;
}