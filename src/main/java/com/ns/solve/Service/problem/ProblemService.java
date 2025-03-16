package com.ns.solve.service.problem;

import com.ns.solve.domain.Solved;
import com.ns.solve.domain.User;
import com.ns.solve.domain.dto.UserDto;
import com.ns.solve.domain.dto.WargameProblemDto;
import com.ns.solve.repository.SolvedRepository;
import com.ns.solve.repository.UserRepository;
import com.ns.solve.utils.ProblemMapper;
import com.ns.solve.domain.dto.ProblemSummary;
import com.ns.solve.domain.problem.Problem;
import com.ns.solve.domain.problem.WargameProblem;
import com.ns.solve.repository.problem.ProblemRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final SolvedRepository solvedRepository;

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

    public WargameProblemDto  markProblemAsChecked(Long id) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        problem.setIsChecked(!problem.getIsChecked());
        Problem savedProblem = problemRepository.save(problem);

        if (savedProblem instanceof WargameProblem) {
            return ProblemMapper.toWargameProblemDto((WargameProblem) savedProblem);
        } else {
            throw new RuntimeException("Not a WargameProblem");
        }
    }

    public WargameProblemDto  getWargameProblemById(Long id) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        if (problem instanceof WargameProblem) {
            return ProblemMapper.toWargameProblemDto((WargameProblem) problem);
        } else {
            throw new RuntimeException("Not of type WargameProblem");
        }
    }

    public List<WargameProblem> getWargameProblems() { return problemRepository.findByType("Wargame"); }

    public Page<Problem> getPendingProblems(PageRequest pageRequest) {
        return problemRepository.findProblemsByStatusPending(pageRequest);
    }

    private Page<Problem> getCompletedProblemsByType(String type, PageRequest pageRequest) {
        return problemRepository.findProblemsByStatusAndType(type, pageRequest);
    }

    public List<ProblemSummary> getCompletedProblemsSummary(String type, PageRequest pageRequest) {
        Page<Problem> problems = getCompletedProblemsByType(type, pageRequest);
        return ProblemMapper.toProblemSummaryList(problems.getContent());
    }


    // problemId에 해당하는 문제의 First Blood가 누구인지 조회
    public Optional<UserDto> firstBlood(Long problemId) {
        Optional<User> user = solvedRepository.findFirstSolverByProblemId(problemId);

        if (user.isPresent()) {
            UserDto userDto = new UserDto(user.get().getNickname(), user.get().getScore(), user.get().getLastActived());
            return Optional.of(userDto);
        }
        return Optional.empty();
    }

    @Transactional
    public Boolean solveProblem(Long userId, Long problemId, String attemptedFlag){
        Optional<User> user = userRepository.findById(userId);
        Problem problem = problemRepository.findProblemWithLock(problemId);
        // 동시성 락 (LockModeType.PESSIMISTIC_WRITE)

        if(problem==null || user.isEmpty()){
            throw new IllegalArgumentException("Problem not found: " + problemId);
        }

        Boolean isCorrect = problemRepository.matchFlagToProblems(problemId, attemptedFlag);

        if (isCorrect) {
            problem.incrementCorrectCount();

            Solved solved = new Solved();
            solved.setSolvedUser(user.get());
            solved.setSolvedProblem(problem);
            solved.setSolvedTime(LocalDateTime.now());
            solvedRepository.save(solved);
        }
        problem.incrementEntireCount();

        problemRepository.save(problem);
        return isCorrect;
    }
}
