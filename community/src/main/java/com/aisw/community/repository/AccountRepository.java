package com.aisw.community.repository;

import com.aisw.community.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
//    Account findByEmail(String email);

    Account findByUsername(String username);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByStudentId(Integer studentId);
}
