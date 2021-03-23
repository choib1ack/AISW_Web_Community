package com.aisw.community.ifs;

import com.aisw.community.model.LoginParam;
import com.aisw.community.model.entity.User;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.AccountApiRequest;
import com.aisw.community.model.network.request.UserApiRequest;
import com.aisw.community.model.network.response.AccountApiResponse;
import com.aisw.community.model.network.response.UserApiResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    Header<AccountApiResponse> signUpUser(Header<AccountApiRequest> request);

    Header<AccountApiResponse> loginUser(LoginParam loginParam);

}
