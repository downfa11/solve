package com.ns.solve.Domain;

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
    private List<Problem.ProblemStatus> problemStatus;
    private List<String> regions;
}
