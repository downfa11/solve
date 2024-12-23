package com.ns.solve.Repository;

import com.ns.solve.Domain.Case;
import java.util.Optional;

public interface CaseCustomRepository {
    Optional<Case> findByCaseId(Long caseId);
}
