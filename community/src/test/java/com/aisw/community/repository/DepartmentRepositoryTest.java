package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.Department;
import com.aisw.community.model.entity.University;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class DepartmentRepositoryTest extends CommunityApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void create() {
        String title = "test";
        String content = "test Content";
        String attachmentFile = "test attachment";
        Long status = 2L;
        Long views = 0L;
        String createdBy = "tester";
        LocalDateTime createdAt = LocalDateTime.now();
        Long level = 1L;

        Department department = Department.builder()
                .title(title)
                .content(content)
                .attachmentFile(attachmentFile)
                .status(status)
                .views(views)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .level(level)
                .build();

        Department newDepartment = departmentRepository.save(department);
        System.out.println("newDepartment: " + newDepartment);
    }

    @Test
    public void read() {
        Optional<Department> department = departmentRepository.findById(1L);

        department.ifPresent(readDepartment -> {
            System.out.println(readDepartment);
        });
    }

    @Test
    @Transactional
    public void update() {
        Optional<Department> department = departmentRepository.findById(1L);

        department.ifPresent(readDepartment -> {
            readDepartment.setLevel(2L);

            departmentRepository.save(readDepartment);
        });
    }

    @Test
    @Transactional
    public void delete() {
        Optional<Department> department = departmentRepository.findById(1L);

        Assertions.assertTrue(department.isPresent());

        department.ifPresent(readDepartment -> {
            departmentRepository.delete(readDepartment);
        });

        Optional<Department> deleteDepartment = departmentRepository.findById(1L);

        Assertions.assertFalse(deleteDepartment.isPresent());
    }
}