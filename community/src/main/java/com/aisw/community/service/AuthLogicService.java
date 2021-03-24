package com.aisw.community.service;

import com.aisw.community.model.LoginParam;
import com.aisw.community.model.entity.Account;
import com.aisw.community.model.enumclass.UserRole;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.AccountApiRequest;
import com.aisw.community.model.network.response.AccountApiResponse;
import com.aisw.community.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthLogicService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //    @Override
    public Header<AccountApiResponse> signUpUser(Header<AccountApiRequest> request){
        // 1. request data
        AccountApiRequest accountApiRequest = request.getData();

        // 2. user create
        Account account = Account.builder()
                .username(accountApiRequest.getUsername())
                .name(accountApiRequest.getName())
                .email(accountApiRequest.getEmail())
                .password(passwordEncoder.encode(accountApiRequest.getPassword()))
                .phoneNumber(accountApiRequest.getPhoneNumber())
                .grade(accountApiRequest.getGrade())
                .studentId(accountApiRequest.getStudentId())
                .level(accountApiRequest.getLevel())
                .job(accountApiRequest.getJob())
                .gender(accountApiRequest.getGender())
                .university(accountApiRequest.getUniversity())
                .collegeName(accountApiRequest.getCollegeName())
                .departmentName(accountApiRequest.getDepartmentName())
                .role(UserRole.STUDENT)
                .build();

        System.out.println(account.getPassword());

        Account newAccount = accountRepository.save(account);

        return Header.OK(response(newAccount));
    }

    public Header<AccountApiResponse> read(Long id) {
        // id -> repository getOne, getById
        Optional<Account> optional = accountRepository.findById(id);

        // user -> userApiResponse return
        return optional
                .map(user -> response(user))
                .map(Header::OK)
                .orElseGet(
                        () -> Header.ERROR("No Data")
                );
    }

    public Header<AccountApiResponse> loginUser(LoginParam request) {
        String email = request.getEmail();
        String password = request.getPassword();
        Optional<Account> optional = accountRepository.findByEmail(email);

        if(optional.isPresent()){
            String inputPW = password;
            return optional.map(user -> loginCheck(user, inputPW))
                    .map(Header::OK)
                    .orElseGet(() -> Header.ERROR("Wrong Password"));
        }
        else
            return Header.ERROR("Email Not Exists");
    }

    public boolean emailDoubleCheck(String email){
        Optional<Account> optional = accountRepository.findByEmail(email);

        if(optional.isPresent())
            return false;
        else
            return true;
    }

    public boolean sidDoubleCheck(Integer studentId){
        Optional<Account> optional = accountRepository.findByStudentId(studentId);

        if(optional.isPresent())
            return false;
        else
            return true;

    }

    public AccountApiResponse loginCheck(Account account, String password){
        if(passwordEncoder.matches(password, account.getPassword()))
            return response(account);
        else
            return null;
    }

    private AccountApiResponse response(Account account){
        // user -> userApiResponse return
        AccountApiResponse accountApiResponse = AccountApiResponse.builder()
                .id(account.getId())
                .username(account.getUsername())
                .name(account.getName())
                .email(account.getEmail())
                .password(account.getPassword())
                .phoneNumber(account.getPhoneNumber())
                .grade(account.getGrade())
                .studentId(account.getStudentId())
                .createdAt(account.getCreatedAt())
                .createdBy(account.getCreatedBy())
                .updatedAt(account.getUpdatedAt())
                .updatedBy(account.getUpdatedBy())
                .level(account.getLevel())
                .job(account.getJob())
                .gender(account.getGender())
                .university(account.getUniversity())
                .collegeName(account.getCollegeName())
                .departmentName(account.getDepartmentName())
                .role(account.getRole())
                .build();

        // Header + data
        return accountApiResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
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
