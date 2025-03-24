package com.ns.solve.service;

import com.ns.solve.domain.Solved;
import com.ns.solve.domain.User;
import com.ns.solve.domain.dto.UserDto;
import com.ns.solve.domain.dto.WargameProblemDto;
import com.ns.solve.domain.problem.Problem;
import com.ns.solve.domain.problem.WargameProblem;
import com.ns.solve.repository.SolvedRepository;
import com.ns.solve.repository.UserRepository;
import com.ns.solve.repository.problem.ProblemRepository;
import com.ns.solve.service.problem.ProblemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProblemServiceTest {

    @Mock private ProblemRepository problemRepository;
    @Mock private UserRepository userRepository;
    @Mock private SolvedRepository solvedRepository;

    @InjectMocks private ProblemService problemService;

    private Problem sampleProblem;
    private WargameProblem wargameProblem;
    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleProblem = new Problem();
        sampleProblem.setId(1L);
        sampleProblem.setTitle("Problem");
        sampleProblem.setIsChecked(false);

        wargameProblem = new WargameProblem();
        wargameProblem.setId(2L);
        wargameProblem.setTitle("Wargame");
        wargameProblem.setIsChecked(false);

        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setNickname("FirstBlood");
        sampleUser.setScore(100L);
        sampleUser.setLastActived(LocalDateTime.now());
    }

    @Test
    void testCreateProblem() {
        when(problemRepository.save(sampleProblem)).thenReturn(sampleProblem);

        Problem created = problemService.createProblem(sampleProblem);

        assertNotNull(created);
        assertEquals(sampleProblem.getTitle(), created.getTitle());
        verify(problemRepository, times(1)).save(sampleProblem);
    }

    @Test
    void testUpdateProblem() {
        Problem updatedProblem = new Problem();
        updatedProblem.setTitle("title");
        updatedProblem.setIsChecked(true);

        when(problemRepository.findById(1L)).thenReturn(Optional.of(sampleProblem));
        when(problemRepository.save(any(Problem.class))).thenReturn(updatedProblem);

        Problem result = problemService.updateProblem(1L, updatedProblem);

        assertNotNull(result);
        assertEquals("title", result.getTitle());
        verify(problemRepository, times(1)).save(any(Problem.class));
    }

    @Test
    void testDeleteProblem() {
        doNothing().when(problemRepository).deleteById(1L);

        problemService.deleteProblem(1L);

        verify(problemRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetProblemById() {
        when(problemRepository.findById(1L)).thenReturn(Optional.of(sampleProblem));

        Optional<Problem> result = problemService.getProblemById(1L);

        assertTrue(result.isPresent());
        assertEquals(sampleProblem.getTitle(), result.get().getTitle());
    }

    @Test
    void testToggleProblemCheckStatus() {
        when(problemRepository.findById(1L)).thenReturn(Optional.of(wargameProblem));
        when(problemRepository.save(any(Problem.class))).thenReturn(wargameProblem);

        WargameProblemDto result = problemService.toggleProblemCheckStatus(1L);

        assertNotNull(result);
        assertEquals(wargameProblem.getTitle(), result.getTitle());
        verify(problemRepository, times(1)).save(wargameProblem);
    }

    @Test
    void testFirstBlood() {
        when(solvedRepository.findFirstSolverByProblemId(1L)).thenReturn(Optional.of(sampleUser));

        Optional<UserDto> result = problemService.firstBlood(1L);

        assertTrue(result.isPresent());
        assertEquals(sampleUser.getNickname(), result.get().nickname());
    }

    @Test
    void testSolveProblem_CorrectFlag() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(problemRepository.findProblemWithLock(1L)).thenReturn(sampleProblem);
        when(problemRepository.matchFlagToProblems(1L, "correct")).thenReturn(true);
        when(solvedRepository.save(any(Solved.class))).thenReturn(new Solved());
        when(problemRepository.save(any(Problem.class))).thenReturn(sampleProblem);

        boolean result = problemService.solveProblem(1L, 1L, "correct");

        assertTrue(result);
        verify(solvedRepository, times(1)).save(any(Solved.class));
    }

    @Test
    void testSolveProblem_WrongFlag() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(problemRepository.findProblemWithLock(1L)).thenReturn(sampleProblem);
        when(problemRepository.matchFlagToProblems(1L, "wrong")).thenReturn(false);
        when(problemRepository.save(any(Problem.class))).thenReturn(sampleProblem);

        boolean result = problemService.solveProblem(1L, 1L, "wrong");

        assertFalse(result);
        verify(solvedRepository, times(0)).save(any(Solved.class));
    }
}
