package com.aisw.community.model.entity;

import com.aisw.community.model.enumclass.NoticeCategory;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = {"universityList", "departmentList", "councilList"})
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private NoticeCategory category;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "notice")
    private List<University> universityList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "notice")
    private List<Department> departmentList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "notice")
    private List<Council> councilList;
}
