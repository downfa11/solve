package com.ns.solve.Repository;

import com.ns.solve.Domain.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long>, AssignmentCustomRepository {
}
