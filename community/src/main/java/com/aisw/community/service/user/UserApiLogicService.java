package com.aisw.community.service.user;

import com.aisw.community.model.entity.user.Account;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.user.AccountApiRequest;
import com.aisw.community.model.network.response.user.AccountApiResponse;
import com.aisw.community.repository.user.AccountRepository;
import com.aisw.community.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApiLogicService extends BaseService<AccountApiRequest, AccountApiResponse, Account> {
    @Autowired
    private AccountRepository accountRepository;

    // 1. request data
    // 2. user create
    // 3. created data -> UserApiResponse return
    @Override
    public Header<AccountApiResponse> create(Header<AccountApiRequest> request) {

        // 1. request data
        AccountApiRequest accountApiRequest = request.getData();

        // 2. user create
        Account account = Account.builder()
                .name(accountApiRequest.getName())
                .email(accountApiRequest.getEmail())
                .password(accountApiRequest.getPassword())
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

    @Override
    public Header<AccountApiResponse> read(Long id) {
        // id -> repository getOne, getById
        Optional<Account> optional = baseRepository.findById(id);

        // user -> userApiResponse return
        return optional
                .map(user -> response(user))
                .map(Header::OK)
                .orElseGet(
                        () -> Header.ERROR("No Data")
                );
    }


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
                    .setPassword(accountApiRequest.getPassword())
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

    private AccountApiResponse response(Account account){
        // user -> userApiResponse return
        AccountApiResponse accountApiResponse = AccountApiResponse.builder()
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
    public Header<List<AccountApiResponse>> search(Pageable pageable) {
        Page<Account> users = baseRepository.findAll(pageable);

        List<AccountApiResponse> accountApiResponseList = users.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(accountApiResponseList);
    }
}
