package com.aisw.community.ifs;

import com.aisw.community.model.LoginParam;
import com.aisw.community.model.entity.User;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.UserApiRequest;
import com.aisw.community.model.network.response.UserApiResponse;

public interface AuthService{
    Header<UserApiResponse> signUpUser(Header<UserApiRequest> request);

    Header<UserApiResponse> loginUser(Header<LoginParam> loginParam);

}
