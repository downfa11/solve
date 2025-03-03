package com.ns.solve.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class WargameProblemSummary extends ProblemSummary {
    private String level;
}
