package com.aisw.community.model.entity.post.file;

import com.aisw.community.model.entity.admin.Banner;
import com.aisw.community.model.entity.admin.SiteInformation;
import com.aisw.community.model.entity.post.Bulletin;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"bulletin"})
@Getter
@Setter
@Builder
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileDownloadUri;

    private String fileType;

    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    private Bulletin bulletin;

    @ManyToOne(fetch = FetchType.LAZY)
    private Banner banner;

    @ManyToOne(fetch = FetchType.LAZY)
    private SiteInformation siteInformation;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;
}
