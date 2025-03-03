package com.ns.solve.repository.user;

import com.ns.solve.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 전체 문제를 푼 개수에 따라서 위에서부터 정렬하여 Pagenation 끊어서 조회하는 메서드
    // 유형별 문제를 푼 개수에 따라서 위에서부터 정렬하여 Pagenation 끊어서 조회하는 메서드
    User findByAccount(String account);
    Page<User> findAllByOrderByScoreDesc(Pageable pageable);
}
