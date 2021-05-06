package com.aisw.community.repository.user;

import com.aisw.community.model.entity.user.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

}
