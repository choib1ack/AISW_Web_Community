package com.aisw.community.ifs;

import com.aisw.community.model.LoginParam;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.user.AccountApiRequest;
import com.aisw.community.model.network.response.user.AccountApiResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    Header<AccountApiResponse> signUpUser(Header<AccountApiRequest> request);

    Header<AccountApiResponse> loginUser(LoginParam loginParam);

}
