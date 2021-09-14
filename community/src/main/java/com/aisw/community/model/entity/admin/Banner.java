package com.aisw.community.model.entity.admin;

import com.aisw.community.model.entity.post.file.File;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
@ToString(exclude = {"fileList"})
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String content;

    private LocalDate startDate;

    private LocalDate endDate;

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

    @BatchSize(size = 1)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "banner", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<File> fileList;

    @PrePersist
    @PreUpdate
    public void checkPublish() {
        LocalDate now = LocalDate.now();
        if((now.isEqual(startDate) || now.isAfter(startDate))
                && (now.isEqual(endDate) || now.isBefore(endDate))) {
            publishStatus = Boolean.TRUE;
        } else {
            publishStatus = Boolean.FALSE;
        }
    }
}
