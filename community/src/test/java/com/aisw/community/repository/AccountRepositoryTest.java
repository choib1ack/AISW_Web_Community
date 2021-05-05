package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.user.Account;
import com.aisw.community.model.enumclass.*;
import com.aisw.community.repository.user.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Optional;

public class AccountRepositoryTest extends CommunityApplicationTests{
    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    private AccountRepository accountRepository;

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

        Account account = Account.builder()
                .name(name)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .grade(grade)
                .studentId(studentId)
                .gender(gender)
                .university(university)
                .collegeName(collegeName)
                .departmentName(departmentName)
                .build();

        Account newUser = accountRepository.save(account);
        Assertions.assertNotNull(newUser);
    }

    @Test
    @Transactional
    public void read(){
        Optional<Account> user = accountRepository.findById(1L);

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

        Optional<Account> user = accountRepository.findByEmail("test02@gmail.com");

        user.ifPresent(selectUser ->{
            selectUser.setPassword(passwordEncoder.encode("pppp1111"));

            Account newUser = accountRepository.save(selectUser);
            System.out.println("user : " + newUser);
        });

    }

    @Test
    public void delete(){
        Optional<Account> user = accountRepository.findById(1L);

        Assertions.assertTrue(user.isPresent());
        user.ifPresent(selectUser ->{
            accountRepository.delete(selectUser);
        });


        Optional<Account> deleteUser = accountRepository.findById(1L);

        if(deleteUser.isPresent()){
            System.out.println("Data Exists : " + deleteUser);
        }
        else{
            System.out.println("No Data");
        }

        Assertions.assertFalse(deleteUser.isPresent());
    }

}
