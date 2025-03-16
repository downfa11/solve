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
    @JoinColumn(name = "solved_user_id", referencedColumnName = "id")
    private User solvedUser;

    @ManyToOne
    @JoinColumn(name = "solved_problem_id", referencedColumnName = "id")
    private Problem solvedProblem;

    private LocalDateTime solvedTime;
}