package com.ns.solve.Domain.dto;

import com.ns.solve.Domain.Problem.ProblemStatus;
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
