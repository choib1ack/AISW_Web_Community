package com.aisw.community.controller.api.admin;

import com.aisw.community.controller.CrudController;
import com.aisw.community.model.entity.admin.AdminUser;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.admin.AdminUserApiRequest;
import com.aisw.community.model.network.response.admin.AdminUserApiResponse;
import com.aisw.community.service.admin.AdminUserApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminUserApiController extends CrudController<AdminUserApiRequest, AdminUserApiResponse, AdminUser> {

    @Autowired
    private AdminUserApiLogicService adminUserApiLogicService;

    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        return adminUserApiLogicService.delete(id);
    }

}
