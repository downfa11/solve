package com.ns.solve.Domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Case {
    private final Long problemId;
    private final CaseKind caseKind;
    private final String input;
    private final String expectedOutput;
}
