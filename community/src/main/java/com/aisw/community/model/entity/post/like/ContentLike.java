package com.aisw.community.model.entity.post.like;

import com.aisw.community.model.entity.post.board.Board;
import com.aisw.community.model.entity.post.comment.Comment;
import com.aisw.community.model.entity.user.Alert;
import com.aisw.community.model.entity.user.User;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"user", "board", "comment", "alertList"})
public class ContentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contentLike", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Alert> alertList;
}
