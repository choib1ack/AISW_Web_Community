package com.aisw.community.service;

import com.aisw.community.model.entity.Account;
import com.aisw.community.model.enumclass.UserRole;
import com.aisw.community.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

//        Account account = userRepository.findByUsername(username);
//
//        if(account == null){
//            throw new UsernameNotFoundException("UsernameNotFoundException");
//        }
//
//        List<GrantedAuthority> roles = new ArrayList<>();
//        roles.add(new SimpleGrantedAuthority(account.getRole().toString()));
//
//        AccountContext accountContext = new AccountContext(account, roles);
//
//        return accountContext;

        Optional<Account> optional = accountRepository.findByEmail(email);

        if(optional.isPresent()){
            Account account = Account.builder()
                    .id(optional.get().getId())
                    .username(optional.get().getUsername())
                    .name(optional.get().getName())
                    .email(optional.get().getEmail())
                    .password(optional.get().getPassword())
                    .phoneNumber(optional.get().getPhoneNumber())
                    .grade(optional.get().getGrade())
                    .studentId(optional.get().getStudentId())
                    .createdAt(optional.get().getCreatedAt())
                    .createdBy(optional.get().getCreatedBy())
                    .updatedAt(optional.get().getUpdatedAt())
                    .updatedBy(optional.get().getUpdatedBy())
                    .level(optional.get().getLevel())
                    .job(optional.get().getJob())
                    .gender(optional.get().getGender())
                    .university(optional.get().getUniversity())
                    .collegeName(optional.get().getCollegeName())
                    .departmentName(optional.get().getDepartmentName())
                    .role(optional.get().getRole())
                    .build();

            List<GrantedAuthority> roles = new ArrayList<>();

            roles.add(new SimpleGrantedAuthority(UserRole.NOT_PERMITTED.toString()));

            AccountContext accountContext = new AccountContext(account, roles);

            return accountContext;
        }
        else{
            throw new UsernameNotFoundException("Email Not Exists.");
        }
    }
}
