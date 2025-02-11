package com.ns.solve.repository;

import com.ns.solve.domain.Case;
import java.util.Optional;

public interface CaseCustomRepository {
    Optional<Case> findByCaseId(Long caseId);
}
