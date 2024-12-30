package com.ns.solve.Domain.dto;

import com.ns.solve.Domain.Case;
import com.ns.solve.Domain.Membership;
import com.ns.solve.Domain.Problem.ProblemStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    private com.ns.solve.Domain.Problem.ProblemStatus status;

    private Timestamp deadline;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Case> caseList;
}