package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.Account;
import com.aisw.community.model.entity.User;
import com.aisw.community.model.enumclass.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Optional;

public class UserRepositoryTest extends CommunityApplicationTests{

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        String name = "Test06";
        String email = "Test06@gmail.com";
        String password = "Test06";
        String phoneNumber = "010-6666-6666";
        Grade grade = Grade.FRESHMAN;
        Integer studentId = 202066666;
        Level level = Level.STUDENT;
        Job job = Job.STUDENT;
        Gender gender = Gender.MALE;
        Campus university = Campus.GLOBAL;
        CollegeName collegeName = CollegeName.IT_CONVERGENCE;
        DepartmentName departmentName = DepartmentName.SOFTWARE;

        Account account = Account.builder()
                .name(name)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .grade(grade)
                .studentId(studentId)
                .level(level)
                .job(job)
                .gender(gender)
                .university(university)
                .collegeName(collegeName)
                .departmentName(departmentName)
                .build();

        Account newUser = userRepository.save(account);
        Assertions.assertNotNull(newUser);
    }

    @Test
    @Transactional
    public void read(){
        Optional<Account> user = userRepository.findById(1L);

//        user.ifPresent(selectUser ->{
////            selectUser.getNoticeList().stream().forEach(notice -> {
////                notice.getUniversityList().stream().forEach(university -> {
////                    System.out.println(university.getTitle());
////                });
////                notice.getDepartmentList().stream().forEach(department -> {
////                    System.out.println(department.getTitle());
////                });
////                notice.getCouncilList().stream().forEach(council -> {
////                    System.out.println(council.getTitle());
////                });
////            });
//            selectUser.getBoardList().stream().forEach(board -> {
//                board.getFreeList().stream().forEach(free -> {
//                    System.out.println(free.getTitle());
//                });
//                board.getQnaList().stream().forEach(qna -> {
//                    System.out.println(qna.getTitle());
//                });
//            });
//        });
    }

    @Test
    @Transactional
    public void update(){
        Optional<Account> user = userRepository.findById(5L);

        user.ifPresent(selectUser ->{
            selectUser.setPassword("pppp1111");

            Account newUser = userRepository.save(selectUser);
            System.out.println("user : " + newUser);
        });

    }

    @Test
    public void delete(){
        Optional<Account> user = userRepository.findById(1L);

        Assertions.assertTrue(user.isPresent());
        user.ifPresent(selectUser ->{
            userRepository.delete(selectUser);
        });


        Optional<Account> deleteUser = userRepository.findById(1L);

        if(deleteUser.isPresent()){
            System.out.println("Data Exists : " + deleteUser);
        }
        else{
            System.out.println("No Data");
        }

        Assertions.assertFalse(deleteUser.isPresent());
    }

}
