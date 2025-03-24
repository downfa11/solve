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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;
    private final SolvedRepository solvedRepository;

    /*
     * 1. Assignment에 Github 주소를 제출한다.
     * 2. 해당 Problem의 Case 들을 불러온다.
     * 3. k8s 환경에서 빌드하고 Case들을 입력한다.
     * 4. 입력에 대한 결과들을 저장하고 정답과 비교, 평가한다. (케이스별 가중치)
     */

    @Transactional
    public Problem createProblem(Problem problem) {
        return problemRepository.save(problem);
    }

    @Transactional
    public Problem updateProblem(Long id, Problem updatedProblem) {
        Problem existingProblem = problemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Problem not found: " + id));

        existingProblem.setTitle(updatedProblem.getTitle());
        existingProblem.setType(updatedProblem.getType());
        existingProblem.setCreator(updatedProblem.getCreator());
        existingProblem.setSolution(updatedProblem.getSolution());

        return problemRepository.save(existingProblem);
    }

    @Transactional
    public void deleteProblem(Long id) {
        problemRepository.deleteById(id);
    }

    public Optional<Problem> getProblemById(Long id) {
        return problemRepository.findById(id);
    }

    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    @Transactional
    public WargameProblemDto toggleProblemCheckStatus(Long id) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Problem not found: " + id));

        problem.setIsChecked(!problem.getIsChecked());
        Problem savedProblem = problemRepository.save(problem);

        if (savedProblem instanceof WargameProblem wargameProblem) {
            return ProblemMapper.toWargameProblemDto(wargameProblem);
        }
        throw new IllegalArgumentException("Not a WargameProblem");
    }

    public WargameProblemDto getWargameProblemById(Long id) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Problem not found: " + id));

        if (problem instanceof WargameProblem wargameProblem) {
            return ProblemMapper.toWargameProblemDto(wargameProblem);
        }
        throw new IllegalArgumentException("Not of type WargameProblem");
    }

    public List<WargameProblem> getAllWargameProblems() {
        return problemRepository.findByType("Wargame");
    }

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
        return solvedRepository.findFirstSolverByProblemId(problemId)
                .map(user -> new UserDto(user.getNickname(), user.getScore(), user.getLastActived()));
    }

    @Transactional
    public boolean solveProblem(Long userId, Long problemId, String attemptedFlag) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Problem problem = problemRepository.findProblemWithLock(problemId);
        if (problem == null) {
            throw new IllegalArgumentException("Problem not found: " + problemId);
        }

        boolean isCorrect = problemRepository.matchFlagToProblems(problemId, attemptedFlag);
        if (isCorrect) {
            problem.incrementCorrectCount();

            Solved solved = new Solved();
            solved.setSolvedUser(user);
            solved.setSolvedProblem(problem);
            solved.setSolvedTime(LocalDateTime.now());
            solvedRepository.save(solved);
        }
        problem.incrementEntireCount();
        problemRepository.save(problem);

        return isCorrect;
    }
}
