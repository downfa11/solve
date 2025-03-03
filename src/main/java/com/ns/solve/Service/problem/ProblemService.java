package com.ns.solve.service.problem;

import com.ns.solve.Utils.ProblemMapper;
import com.ns.solve.domain.dto.ProblemSummary;
import com.ns.solve.domain.problem.Problem;
import com.ns.solve.domain.problem.WargameProblem;
import com.ns.solve.repository.problem.ProblemRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;

    // CRUD
    // 각 문제 페이지를 조회
    // 문제 종류별 목록(ProblemSummary)을 조회
    // 운영진의 검수 과정(check)

    public Problem createProblem(Problem problem) { return problemRepository.save(problem); }

    public Problem updateProblem(Long id, Problem updatedProblem) {
        Problem existingProblem = problemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        existingProblem.setTitle(updatedProblem.getTitle());
        existingProblem.setType(updatedProblem.getType());
        existingProblem.setCreator(updatedProblem.getCreator());
        existingProblem.setSolution(updatedProblem.getSolution());
        return problemRepository.save(existingProblem);
    }

    public void deleteProblem(Long id) { problemRepository.deleteById(id); }
    public Optional<Problem> getProblemById(Long id) { return problemRepository.findById(id); }
    public List<Problem> getAllProblems() { return problemRepository.findAll(); }

    public Problem markProblemAsChecked(Long id) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        problem.setIsChecked(!problem.getIsChecked());
        return problemRepository.save(problem);
    }

    public WargameProblem getWargameProblemById(Long id) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        if (problem instanceof WargameProblem) {
            return (WargameProblem) problem;
        } else {
            throw new RuntimeException("The problem is not of type WargameProblem");
        }
    }

    public List<WargameProblem> getWargameProblems() { return problemRepository.findByType("Wargame"); }


    public Problem incrementEntireCount(Long problemId) {
        Problem problem = problemRepository.findById(problemId).orElseThrow(() -> new RuntimeException("Problem not found"));
        problem.incrementEntireCount();
        return problemRepository.save(problem);
    }

    public Problem incrementCorrectCount(Long problemId) {
        Problem problem = problemRepository.findById(problemId).orElseThrow(() -> new RuntimeException("Problem not found"));
        problem.incrementCorrectCount();
        return problemRepository.save(problem);
    }

    public Page<Problem> getPendingProblems(PageRequest pageRequest) {
        return problemRepository.findProblemsByStatusPending(pageRequest);
    }

    public Page<Problem> getCompletedProblemsByType(String type, PageRequest pageRequest) {
        return problemRepository.findProblemsByStatusAndType(type, pageRequest);
    }

    public List<ProblemSummary> getCompletedProblemsSummary(String type, PageRequest pageRequest) {
        Page<Problem> problems = getCompletedProblemsByType(type, pageRequest);
        return ProblemMapper.toProblemSummaryList(problems.getContent());
    }
}
