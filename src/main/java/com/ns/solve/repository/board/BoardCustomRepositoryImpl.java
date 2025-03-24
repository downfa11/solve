package com.ns.solve.repository.board;

import com.ns.solve.domain.QBoard;
import com.ns.solve.domain.dto.BoardSummary;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardCustomRepositoryImpl implements BoardCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QBoard qBoard;

    public BoardCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
        this.qBoard = QBoard.board;
    }


    @Override
    public Page<BoardSummary> findBoardsByPage(Pageable pageable, boolean desc) {
        List<BoardSummary> boardSummaries = jpaQueryFactory
                .selectFrom(qBoard)
                // .leftJoin(qBoard.creator).fetchJoin() n+1
                .orderBy(desc ? qBoard.createdAt.desc() : qBoard.createdAt.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(board -> new BoardSummary(
                        board.getId(),
                        board.getTitle(),
                        board.getCreator().getNickname(),
                        board.getUpdatedAt(),
                        (long) board.getCommentList().size()) // commentList의 크기
                )
                .toList();

        long total = jpaQueryFactory
                .selectFrom(qBoard)
                .fetchCount();

        return new PageImpl<>(boardSummaries, pageable, total);
    }
}
