package com.ns.solve.domain.dto;

import com.ns.solve.domain.Problem.ProblemStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemFilter{
    private List<ProblemStatus> problemStatus;
    private List<String> regions;
}
