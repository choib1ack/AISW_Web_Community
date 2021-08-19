package com.aisw.community.controller.admin;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.admin.UserManagementApiRequest;
import com.aisw.community.model.network.response.admin.UserManagementApiResponse;
import com.aisw.community.service.admin.UserManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/auth-admin")
public class UserManagementController {

    @Autowired
    private UserManagementService userManagementService;

    @GetMapping("/users")
    public Header<List<UserManagementApiResponse>> readAll(@PageableDefault(sort = {"id"}) Pageable pageable) {
        return userManagementService.readAll(pageable);
    }

    @PutMapping("/user")
    public Header<UserManagementApiResponse> changeRole(@RequestBody Header<UserManagementApiRequest> request) {
        return userManagementService.changeRole(request);
    }
}
