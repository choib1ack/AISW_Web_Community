package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.AdminUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public class AdminUserRepositoryTest extends CommunityApplicationTests{

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Test
    public void create(){
        String name = "AdminUser01";
        String email = "AdminUser01@gmail.com";
        String password = "AdminUser01";
        String phoneNumber = "010-1111-1111";
        LocalDateTime updatedAt = LocalDateTime.now();
        String updatedBy = "AdminServer";
        String role = "AdminUser";

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
        Optional<AdminUser> adminUser = adminUserRepository.findById(1L);

        adminUser.ifPresent(selectUser ->{
            System.out.println("user : " + selectUser);
        });
    }

    @Test
    @Transactional
    public void update(){
        Optional<AdminUser> adminUser = adminUserRepository.findById(1L);

        adminUser.ifPresent(selectUser ->{
            selectUser.setPassword("11111111");

            AdminUser newUser = adminUserRepository.save(selectUser);
            System.out.println("user : " + selectUser);
        });

    }

    @Test
    public void delete(){
        Optional<AdminUser> user = adminUserRepository.findById(1L);

        Assertions.assertTrue(user.isPresent());
        user.ifPresent(selectUser ->{
            adminUserRepository.delete(selectUser);
        });


        Optional<AdminUser> deleteUser = adminUserRepository.findById(1L);

        if(deleteUser.isPresent()){
            System.out.println("Data Exists : " + deleteUser);
        }
        else{
            System.out.println("No Data");
        }

        Assertions.assertFalse(deleteUser.isPresent());
    }

}
