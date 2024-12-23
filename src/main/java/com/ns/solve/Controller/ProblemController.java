package com.ns.solve.Controller;

import com.ns.solve.Domain.dto.ProblemDto;
import com.ns.solve.Service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/problem")
@RequiredArgsConstructor
public class ProblemController {
    private final ProblemService problemService;

    @PostMapping("/register/public")
    public void registerPublicProblem(@RequestBody ProblemDto problemDto){
        problemService.registerProblem(true, problemDto);
    }
    @PostMapping("/register/private")
    public void registerPrivateProblem(@RequestBody ProblemDto problemDto){
        problemService.registerProblem(false, problemDto);
    }

    @GetMapping("/manage/{problemId}")
    public void monitorProblem(@PathVariable Long problemId){
        problemService.monitorProblem(problemId);
    }

    @PutMapping("/update/{problemId}")
    public void updateProblem(@PathVariable Long problemId, @RequestParam Boolean isPublic, @RequestBody ProblemDto problemDto){
        problemService.updateProblem(problemId, isPublic, problemDto);
    }

    @DeleteMapping("/delete/{problemId}")
    public void deleteProblem(@PathVariable Long problemId){
        problemService.deleteProblem(problemId);
    }

    @GetMapping("/{problemId}")
    public void findProblem(@PathVariable Long problemId){
        problemService.findProblem(problemId);
    }


}
