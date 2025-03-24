package com.ns.solve.domain.problem;

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
    private String flag;

    @Column(nullable = true)
    private String dockerfileLink;

    @Column(nullable = true)
    private String problemFile;
}