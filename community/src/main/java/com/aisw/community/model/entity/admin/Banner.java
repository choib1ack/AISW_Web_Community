package com.aisw.community.model.entity.admin;

import com.aisw.community.model.entity.post.file.File;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String content;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean publishStatus;

    private String linkUrl;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "banner", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<File> fileList;

    @PrePersist
    @PreUpdate
    public void checkPublish() {
        LocalDateTime now = LocalDateTime.now();
        if(now.isAfter(startDate) && now.isBefore(endDate)) {
            publishStatus = Boolean.TRUE;
        } else {
            publishStatus = Boolean.FALSE;
        }
    }
}
