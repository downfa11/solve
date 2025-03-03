package com.ns.solve.domain.problem;

import com.ns.solve.domain.dto.ProblemSummary;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class WargameProblem extends Problem {

    private String level;

    @Column(nullable = true)
    private String dockerfileLink;

    @Column(nullable = true)
    private String problemFile;
}