package com.aisw.community.repository.post.board;

import com.aisw.community.model.entity.post.board.Board;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.aisw.community.model.entity.post.board.QBoard.board;

@Repository
@RequiredArgsConstructor
public class CustomBoardRepositoryImpl implements CustomBoardRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Board> findAll(Pageable pageable) {
        System.out.println(123);
        QueryResults<Board> queryResults = jpaQueryFactory.selectFrom(board)
                .leftJoin(board.fileList)
                .fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createdAt.desc())
                .fetchResults();
        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    @Override
    public Page<Board> findAllByWriterContaining(String writer, Pageable pageable) {
        QueryResults<Board> queryResults = jpaQueryFactory.selectFrom(board)
                .leftJoin(board.fileList)
                .fetchJoin()
                .where(board.writer.contains(writer))
                .orderBy(board.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    @Override
    public Page<Board> findAllByTitleContaining(String title, Pageable pageable) {
        QueryResults<Board> queryResults = jpaQueryFactory.selectFrom(board)
                .leftJoin(board.fileList)
                .fetchJoin()
                .where(board.title.contains(title))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createdAt.desc())
                .fetchResults();
        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    @Override
    public Page<Board> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable) {
        QueryResults<Board> queryResults = jpaQueryFactory.selectFrom(board)
                .leftJoin(board.fileList)
                .fetchJoin()
                .where(board.title.contains(title), board.content.contains(content))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createdAt.desc())
                .fetchResults();
        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    @Override
    public Page<Board> findAllByStatusIn(List<BulletinStatus> statusList, Pageable pageable) {
        QueryResults<Board> queryResults = jpaQueryFactory.selectFrom(board)
                .leftJoin(board.fileList)
                .fetchJoin()
                .where(board.status.in(statusList))
                .orderBy(board.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }
}
