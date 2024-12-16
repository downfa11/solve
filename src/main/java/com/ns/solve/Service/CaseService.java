package com.ns.solve.Service;

import com.ns.solve.Domain.dto.AssignmentDto;
import com.ns.solve.Domain.dto.CaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CaseService {
    public void registerCase(CaseDto caseDto) {
        System.out.println("Case 등록: " + caseDto);
    }

    public void updateCase(Long assignmentId, Long caseId, CaseDto caseDto) {
        System.out.println("Case 업데이트: ID = " + assignmentId);
    }

    public void deleteCase(Long assignmentId, Long caseId) {
        System.out.println("Case 삭제: ID = " + assignmentId);
    }

    public void findCaseList(Long assignmentId) {
        System.out.println("Case 검색: ID = " + assignmentId);
    }

}
