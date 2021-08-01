package com.aisw.community.service.user;

import com.aisw.community.model.entity.user.Account;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.user.AccountApiRequest;
import com.aisw.community.model.network.response.user.AccountApiResponse;
import com.aisw.community.repository.user.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountApiService {

//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    public Header<AccountApiResponse> signup(Header<AccountApiRequest> request) {
        AccountApiRequest accountApiRequest = request.getData();
        if(accountApiRequest.getProvider() != null && accountApiRequest.getProviderId() != null) {
            Account account = Account.builder()
                    .username(accountApiRequest.getProvider()+"_"+accountApiRequest.getProviderId())
                    .name(accountApiRequest.getName())
                    .email(accountApiRequest.getEmail())
                    .password("1234")
                    .phoneNumber(accountApiRequest.getPhoneNumber())
                    .grade(accountApiRequest.getGrade())
                    .studentId(accountApiRequest.getStudentId())
                    .gender(accountApiRequest.getGender())
                    .university(accountApiRequest.getUniversity())
                    .collegeName(accountApiRequest.getCollegeName())
                    .departmentName(accountApiRequest.getDepartmentName())
                    .role(accountApiRequest.getRole())
                    .build();

            Account newAccount = accountRepository.save(account);
            return Header.OK(response(newAccount));
        }
        return Header.ERROR("request is wrong");

        // Token 보내기
    }

    private AccountApiResponse response(Account account) {
        AccountApiResponse accountApiResponse = AccountApiResponse.builder()
                .id(account.getId())
                .username(account.getUsername())
                .name(account.getName())
                .email(account.getEmail())
                .phoneNumber(account.getPhoneNumber())
                .grade(account.getGrade())
                .studentId(account.getStudentId())
                .gender(account.getGender())
                .university(account.getUniversity())
                .collegeName(account.getCollegeName())
                .departmentName(account.getDepartmentName())
                .role(account.getRole())
                .createdAt(account.getCreatedAt())
                .createdBy(account.getCreatedBy())
                .updatedAt(account.getUpdatedAt())
                .updatedBy(account.getUpdatedBy())
                .build();

        return accountApiResponse;
    }
}
