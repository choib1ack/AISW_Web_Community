package com.aisw.community.repository.post.comment;

import com.aisw.community.model.entity.post.comment.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.aisw.community.model.entity.post.comment.QComment.comment;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CustomCommentRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> findCommentByBoardId(Long id) {
        return jpaQueryFactory.selectFrom(comment)
                .leftJoin(comment.superComment)
                .fetchJoin()
                .where(comment.board.id.eq(id))
                .orderBy(
                        comment.superComment.id.asc().nullsFirst(),
                        comment.createdAt.asc())
                .fetch();
    }
}
