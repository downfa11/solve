package com.ns.solve.Repository;

import com.ns.solve.Domain.Assignment;
import java.util.Optional;

public interface AssignmentCustomRepository {
    Optional<Assignment> findByAssignmentId(Long assignmentId);
}
