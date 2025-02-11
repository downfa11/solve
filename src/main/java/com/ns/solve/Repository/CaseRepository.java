package com.ns.solve.repository;

import com.ns.solve.domain.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long>, CaseCustomRepository {
}
