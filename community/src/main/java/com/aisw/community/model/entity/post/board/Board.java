package com.aisw.community.model.entity.post.board;

import com.aisw.community.model.entity.post.Bulletin;
import com.aisw.community.model.entity.post.comment.Comment;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
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

    @ColumnDefault("0")
    private Long likes;

    @Enumerated(EnumType.STRING)
    private SecondCategory category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    private List<Comment> commentList;

    public Board(Long id, String title, String writer, String content, BulletinStatus status, Long views,
                 FirstCategory firstCategory, SecondCategory secondCategory, User user, Long likes) {
        super(id, title, writer, content, status, views, firstCategory, secondCategory, user);
        this.likes = likes;
        this.category = secondCategory;
    }
}
