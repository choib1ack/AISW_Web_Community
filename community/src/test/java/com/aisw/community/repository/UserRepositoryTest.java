package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends CommunityApplicationTests{

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        String name = "Test03";
        String email = "Test03@gmail.com";
        String password = "Test03";
        String phoneNumber = "010-3333-3333";
        Integer grade = 1;
        Integer studentId = 202033333;
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";
        LocalDateTime updatedAt = LocalDateTime.now();
        String updatedBy = "AdminServer";
        Integer level = 1;
        String job = "Student";
        Integer gender = 1;
        String university = "Global Campus";
        String college = "IT Convergence";
        String department = "Software";

        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .grade(grade)
                .studentId(studentId)
                .createdAt(createdAt)
                .createdBy(createdBy)
                .updatedAt(updatedAt)
                .updatedBy(updatedBy)
                .level(level)
                .job(job)
                .gender(gender)
                .university(university)
                .college(college)
                .department(department)
                .build();

        User newUser = userRepository.save(user);
        Assertions.assertNotNull(newUser);
    }

    @Test
    @Transactional
    public void read(){
        Optional<User> user = userRepository.findById(1L);

        user.ifPresent(selectUser ->{
            System.out.println("user : " + selectUser);
        });
    }

    @Test
    @Transactional
    public void update(){
        Optional<User> user = userRepository.findById(1L);

        user.ifPresent(selectUser ->{
            selectUser.setPassword("pppp1111");
            selectUser.setUpdatedBy("Update Method");

            User newUser = userRepository.save(selectUser);
            System.out.println("user : " + selectUser);
        });

    }

    @Test
    public void delete(){
        Optional<User> user = userRepository.findById(1L);

        Assertions.assertTrue(user.isPresent());
        user.ifPresent(selectUser ->{
            userRepository.delete(selectUser);
        });


        Optional<User> deleteUser = userRepository.findById(1L);

        if(deleteUser.isPresent()){
            System.out.println("Data Exists : " + deleteUser);
        }
        else{
            System.out.println("No Data");
        }

        Assertions.assertFalse(deleteUser.isPresent());
    }

}
