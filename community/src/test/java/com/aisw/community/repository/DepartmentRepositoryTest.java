package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.Account;
import com.aisw.community.model.entity.Department;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class DepartmentRepositoryTest extends CommunityApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void create() {
        String title = "test";
        String writer = "writer";
        String content = "test Content";
        String attachmentFile = "test attachment";
        BulletinStatus status = BulletinStatus.GENERAL;
        Long views = 0L;
        Account userId = accountRepository.getOne(1L);

        Department department = Department.builder()
                .title(title)
                .writer(writer)
                .content(content)
                .attachmentFile(attachmentFile)
                .status(status)
                .views(views)
                .firstCategory(FirstCategory.NOTICE)
                .secondCategory(SecondCategory.DEPARTMENT)
                .account(userId)
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