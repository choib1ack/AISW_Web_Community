package com.aisw.community.model.entity.post.board;

import com.aisw.community.model.entity.post.Bulletin;
import com.aisw.community.model.entity.post.comment.Comment;
import com.aisw.community.model.entity.user.Account;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"commentList"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("board")
public class Board extends Bulletin {

    private Long likes;

    private Boolean isAnonymous;

    @Enumerated(EnumType.STRING)
    private SecondCategory category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    private List<Comment> commentList;

    public Board(Long id, String title, String writer, String content, BulletinStatus status, Long views,
                 FirstCategory firstCategory, SecondCategory secondCategory, Account account, Long likes,
                 Boolean isAnonymous) {
        super(id, title, writer, content, status, views, firstCategory, secondCategory, account);
        this.likes = likes;
        this.isAnonymous = isAnonymous;
        this.category = secondCategory;
    }
}
