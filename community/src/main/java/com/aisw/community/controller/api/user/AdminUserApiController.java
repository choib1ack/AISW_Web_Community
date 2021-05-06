package com.aisw.community.controller.api.user;

import com.aisw.community.controller.CrudController;
import com.aisw.community.model.entity.user.AdminUser;
import com.aisw.community.model.network.request.user.AdminUserApiRequest;
import com.aisw.community.model.network.response.user.AdminUserApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminUserApiController extends CrudController<AdminUserApiRequest, AdminUserApiResponse, AdminUser> {
}
