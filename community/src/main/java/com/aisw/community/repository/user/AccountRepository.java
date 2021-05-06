package com.aisw.community.repository.user;

import com.aisw.community.model.entity.user.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
//    Account findByEmail(String email);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByStudentId(Integer studentId);
}
