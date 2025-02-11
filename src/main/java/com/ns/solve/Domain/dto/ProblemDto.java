package com.ns.solve.domain.dto;

import com.ns.solve.domain.Case;
import com.ns.solve.domain.Problem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDto {
    private Long membershipId;
    private String title;
    private String detail;

    private Problem.ProblemStatus status;

    private Timestamp deadline;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Case> caseList;
}