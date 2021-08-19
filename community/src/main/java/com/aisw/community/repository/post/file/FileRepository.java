package com.aisw.community.repository.post.file;

import com.aisw.community.model.entity.post.file.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findAllByBulletinId(Long id);
    List<File> findAllBySiteInformationId(Long id);
    List<File> findAllByBannerId(Long id);
}
