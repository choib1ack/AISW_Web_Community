package com.aisw.community.service.user;

import com.aisw.community.ifs.AuthService;
import com.aisw.community.model.LoginParam;
import com.aisw.community.model.entity.user.Account;
import com.aisw.community.model.enumclass.UserRole;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.user.AccountApiRequest;
import com.aisw.community.model.network.response.user.AccountApiResponse;
import com.aisw.community.repository.user.AccountRepository;
import com.aisw.community.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Authentication 인증
@Service
public class AuthLogicService extends BaseService<AccountApiRequest, AccountApiResponse, Account> {

    @Autowired
    private AccountRepository accountRepository;


    //    @Override
    public Header<AccountApiResponse> signUpUser(Header<AccountApiRequest> request) {
        // 1. request data

        AccountApiRequest accountApiRequest = request.getData();

        // 이미 가입한 유저가 회원가입할 경우
        if (emailDoubleCheck(accountApiRequest.getEmail()))
            return Header.ERROR("Already Exists User");

        // 이미 존재하는 학번을 첬을 경우
        if (!sidDoubleCheck(accountApiRequest.getStudentId()))
            return Header.ERROR("Student Id Already Exists");

        // 2. user create
        Account account = Account.builder()
                .name(accountApiRequest.getName())
                .email(accountApiRequest.getEmail())
                .phoneNumber(accountApiRequest.getPhoneNumber())
                .grade(accountApiRequest.getGrade())
                .studentId(accountApiRequest.getStudentId())
                .gender(accountApiRequest.getGender())
                .university(accountApiRequest.getUniversity())
                .collegeName(accountApiRequest.getCollegeName())
                .departmentName(accountApiRequest.getDepartmentName())
                .role(UserRole.STUDENT)
                .build();


        Account newAccount = accountRepository.save(account);

        return Header.OK(response(newAccount));
    }

    @Override
    public Header<AccountApiResponse> create(Header<AccountApiRequest> request) {
        // 1. request data
        AccountApiRequest accountApiRequest = request.getData();

        // 2. user create
        Account account = Account.builder()
                .name(accountApiRequest.getName())
                .email(accountApiRequest.getEmail())
                .phoneNumber(accountApiRequest.getPhoneNumber())
                .grade(accountApiRequest.getGrade())
                .studentId(accountApiRequest.getStudentId())
                .gender(accountApiRequest.getGender())
                .university(accountApiRequest.getUniversity())
                .collegeName(accountApiRequest.getCollegeName())
                .departmentName(accountApiRequest.getDepartmentName())
                .role(accountApiRequest.getRole())
                .build();

        Account newAccount = baseRepository.save(account);

        // 3. created user -> userApiResponse return : public Header<UserApiResponse> response(User user){}
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

    public Header<AccountApiResponse> loginUser(String email) {
        // merge 후에 준헌이 형이 만들어둔 exception 적용하기
        // email double check 해서 로그인을 결정해줌
        return accountRepository.findByEmail(email)
                .map(account -> Header.OK(response(account)))
                .orElseGet(() -> Header.ERROR("Email Not Exists"));
    }

    // 회원 가입 할때 email double check
    public boolean emailDoubleCheck(String email) {
        Optional<Account> optional = accountRepository.findByEmail(email);
        if (optional.isPresent())
            return false;
        else
            return true;
    }

    public boolean sidDoubleCheck(Integer studentId) {
        Optional<Account> optional = accountRepository.findByStudentId(studentId);

        if (optional.isPresent())
            return false;
        else
            return true;

    }


    private AccountApiResponse response(Account account) {
        // user -> userApiResponse return
        AccountApiResponse accountApiResponse = AccountApiResponse.builder()
                .id(account.getId())
                .name(account.getName())
                .email(account.getEmail())
                .phoneNumber(account.getPhoneNumber())
                .grade(account.getGrade())
                .studentId(account.getStudentId())
                .createdAt(account.getCreatedAt())
                .createdBy(account.getCreatedBy())
                .updatedAt(account.getUpdatedAt())
                .updatedBy(account.getUpdatedBy())
                .gender(account.getGender())
                .university(account.getUniversity())
                .collegeName(account.getCollegeName())
                .departmentName(account.getDepartmentName())
                .role(account.getRole())
                .build();

        // Header + data
        return accountApiResponse;
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<Account> optional = accountRepository.findByEmail(email);
//
//        if (optional.isPresent()) {
//            Account account = Account.builder()
//                    .id(optional.get().getId())
//                    .name(optional.get().getName())
//                    .email(optional.get().getEmail())
//                    .phoneNumber(optional.get().getPhoneNumber())
//                    .grade(optional.get().getGrade())
//                    .studentId(optional.get().getStudentId())
//                    .createdAt(optional.get().getCreatedAt())
//                    .createdBy(optional.get().getCreatedBy())
//                    .updatedAt(optional.get().getUpdatedAt())
//                    .updatedBy(optional.get().getUpdatedBy())
//                    .gender(optional.get().getGender())
//                    .university(optional.get().getUniversity())
//                    .collegeName(optional.get().getCollegeName())
//                    .departmentName(optional.get().getDepartmentName())
//                    .role(optional.get().getRole())
//                    .build();
//
//            List<GrantedAuthority> roles = new ArrayList<>();
//
//            roles.add(new SimpleGrantedAuthority(UserRole.NOT_PERMITTED.toString()));
//
//            AccountContext accountContext = new AccountContext(account, roles);
//
//            return accountContext;
//        } else {
//            throw new UsernameNotFoundException("Email Not Exists.");
//        }
//    }


    @Override
    public Header<AccountApiResponse> update(Header<AccountApiRequest> request) {
        // 1. data
        AccountApiRequest accountApiRequest = request.getData();

        System.out.println(accountApiRequest);

        // 2. id -> user data find
        Optional<Account> optional = accountRepository.findByEmail(accountApiRequest.getEmail());

        return optional.map(user ->
                // 3. data -> update
                // id
                response(user
                        .setName(accountApiRequest.getName())
                        .setEmail(accountApiRequest.getEmail())
                        .setPhoneNumber(accountApiRequest.getPhoneNumber())
                        .setGrade(accountApiRequest.getGrade())
                        .setStudentId(accountApiRequest.getStudentId())
                        .setGender(accountApiRequest.getGender())
                        .setUniversity(accountApiRequest.getUniversity())
                        .setCollegeName(accountApiRequest.getCollegeName())
                        .setDepartmentName(accountApiRequest.getDepartmentName())
                        .setRole(accountApiRequest.getRole()))
        ).map(Header::OK)
                .orElseGet(() -> Header.ERROR("No Data"));
    }

    @Override
    public Header delete(Long id) {
        // 1. id -> repository -> user
        Optional<Account> optional = baseRepository.findById(id);

        // 2. repository -> delete
        // 3. response return
        return optional.map(user -> {
            baseRepository.delete(user);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("No Data"));
    }

    @Override
    public Header<List<AccountApiResponse>> search(Pageable pageable) {
        Page<Account> users = baseRepository.findAll(pageable);

        List<AccountApiResponse> accountApiResponseList = users.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(accountApiResponseList);
    }
}
