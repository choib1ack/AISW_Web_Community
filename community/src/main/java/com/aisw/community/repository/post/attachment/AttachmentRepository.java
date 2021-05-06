package com.aisw.community.repository.post.attachment;

import com.aisw.community.model.entity.post.attachment.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findAllByBulletinId(Long id);
}
