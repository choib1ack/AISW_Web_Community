package com.aisw.community.model.entity.post.attachment;

import com.aisw.community.model.entity.post.Bulletin;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"bulletin"})
@Getter
@Setter
@Builder
@Accessors(chain = true)
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originFileName;

    private String fileName;

    private String filePath;

    private String fileType;

    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    private Bulletin bulletin; // bulletin id

}
