package com.aisw.community.controller.api;

import com.aisw.community.controller.CrudController;
import com.aisw.community.model.entity.User;
import com.aisw.community.model.network.request.UserApiRequest;
import com.aisw.community.model.network.response.UserApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserApiController extends CrudController<UserApiRequest, UserApiResponse, User> {
}
