package com.ns.solve.Utils;

import com.ns.solve.domain.dto.ProblemSummary;
import com.ns.solve.domain.problem.Problem;
import com.ns.solve.domain.problem.WargameProblem;

import java.util.List;
import java.util.stream.Collectors;

public class ProblemMapper {

    public static ProblemSummary toProblemSummary(Problem problem) {
        if (problem instanceof WargameProblem) {
            return toWargameProblemSummary((WargameProblem) problem);
        } else {
            return ProblemSummary.builder()
                    .id(problem.getId())
                    .title(problem.getTitle())
                    .creator(problem.getCreator())
                    .type(problem.getType())
                    .correctRate(problem.getCorrectCount() / problem.getEntireCount())
                    .build();
        }
    }

    public static ProblemSummary toWargameProblemSummary(WargameProblem problem) {
        return ProblemSummary.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .creator(problem.getCreator())
                .type(problem.getType())
                .correctRate(problem.getCorrectCount() / problem.getEntireCount())
                .level(problem.getLevel())
                .build();
    }

    public static List<ProblemSummary> toProblemSummaryList(List<Problem> problems) {
        return problems.stream()
                .map(ProblemMapper::toProblemSummary)
                .collect(Collectors.toList());
    }
}
