package com.ns.solve.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemSummary {
    private Long id;
    private String title;
    private String level;
    private Double correctRate;
    private String creator;
    private String type;
}
