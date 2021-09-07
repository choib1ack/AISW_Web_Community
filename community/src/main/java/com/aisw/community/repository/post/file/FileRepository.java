package com.aisw.community.repository.post.file;

import com.aisw.community.model.entity.post.file.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findAllByStoredFileNameStartingWithAndStoredFileNameEndsWith(String prefix, String extension);
}
