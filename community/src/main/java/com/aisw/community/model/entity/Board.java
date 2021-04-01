package com.aisw.community.model.entity;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(exclude = {"commentList", "contentLikeList"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("board")
public class Board extends Bulletin {

    private Long likes;

    private Boolean isAnonymous;

    private SecondCategory category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    private List<Comment> commentList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL)
    private Set<ContentLike> contentLikeList;

    public Board(Long id, String title, String writer, String content, String attachmentFile, BulletinStatus status,
                 Long views, FirstCategory firstCategory, SecondCategory secondCategory, Account account,
                 Long likes, Boolean isAnonymous) {
        super(id, title, writer, content, attachmentFile, status, views, firstCategory, secondCategory, account);
        this.likes = likes;
        this.isAnonymous = isAnonymous;
        this.category = secondCategory;
    }
}
