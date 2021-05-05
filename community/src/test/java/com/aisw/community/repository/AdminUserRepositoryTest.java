package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.user.AdminUser;
import com.aisw.community.repository.user.AdminUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Optional;

public class AdminUserRepositoryTest extends CommunityApplicationTests {

    @Autowired
    AdminUserRepository adminUserRepository;

    @Test
    public void create(){
        String name = "AdminUser02";
        String email = "AdminUser02@gmail.com";
        String password = "AdminUser02";
        String phoneNumber = "010-2222-2222";
        String role = "Management";

        AdminUser adminUser = AdminUser.builder()
                .name(name)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .role(role)
                .build();

        AdminUser newAdminUser = adminUserRepository.save(adminUser);
        Assertions.assertNotNull(newAdminUser);
    }

    @Test
    @Transactional
    public void read(){
        Optional<AdminUser> optional = adminUserRepository.findById(2L);

        optional.ifPresent(selectUser -> {
            System.out.println("AdminUser : " + selectUser);
        });
    }

    @Test
    @Transactional
    public void update(){
        Optional<AdminUser> optional = adminUserRepository.findById(2L);

        optional.ifPresent(selectUser -> {
            selectUser.setPassword("pppp2222");

            AdminUser newAdminUser = adminUserRepository.save(selectUser);
            System.out.println("AdminUser : " + newAdminUser);

        });
    }

    @Test
    public void delete(){
        Optional<AdminUser> optional = adminUserRepository.findById(2L);

        Assertions.assertTrue(optional.isPresent());
        optional.ifPresent(selectUser ->{
            adminUserRepository.delete(selectUser);
        });


        Optional<AdminUser> deleteUser = adminUserRepository.findById(2L);

        if(deleteUser.isPresent()){
            System.out.println("Data Exists : " + deleteUser);
        }
        else{
            System.out.println("No Data");
        }

        Assertions.assertFalse(deleteUser.isPresent());
    }

}
