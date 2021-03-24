package com.aisw.community.controller.api;

import com.aisw.community.controller.NoticePostController;
import com.aisw.community.model.entity.Department;
import com.aisw.community.model.network.request.DepartmentApiRequest;
import com.aisw.community.model.network.response.DepartmentApiResponse;
import com.aisw.community.model.network.response.NoticeResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notice/department")
public class DepartmentApiController extends NoticePostController<DepartmentApiRequest, NoticeResponseDTO, DepartmentApiResponse, Department> {
}
