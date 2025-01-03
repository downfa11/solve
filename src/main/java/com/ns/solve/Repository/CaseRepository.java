package com.ns.solve.Repository;

import com.ns.solve.Domain.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long>, CaseCustomRepository {
}
