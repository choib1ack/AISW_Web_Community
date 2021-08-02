package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.*;
import com.aisw.community.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Optional;

public class UserRepositoryTest extends CommunityApplicationTests{
//    @Autowired
//    PasswordEncoder passwordEncoder;


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
        Gender gender = Gender.MALE;
        Campus university = Campus.GLOBAL;
        CollegeName collegeName = CollegeName.IT_CONVERGENCE;
        DepartmentName departmentName = DepartmentName.SOFTWARE;

        User user = User.builder()
                .name(name)
                .email(email)
//                .password(password)
                .phoneNumber(phoneNumber)
                .grade(grade)
                .studentId(studentId)
                .gender(gender)
                .university(university)
                .collegeName(collegeName)
                .departmentName(departmentName)
                .build();

        User newUser = userRepository.save(user);
        Assertions.assertNotNull(newUser);
    }

    @Test
    @Transactional
    public void read(){
        Optional<User> user = userRepository.findById(1L);

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

//        Optional<User> user = userRepository.findByEmail("test02@gmail.com");

//        user.ifPresent(selectUser ->{
//            selectUser.setPassword(passwordEncoder.encode("pppp1111"));

//            Account newUser = accountRepository.save(selectUser);
//            System.out.println("user : " + newUser);
//        });

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
