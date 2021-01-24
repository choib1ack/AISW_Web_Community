package com.aisw.community.controller.api;

import com.aisw.community.controller.CrudController;
import com.aisw.community.model.entity.Department;
import com.aisw.community.model.network.request.DepartmentApiRequest;
import com.aisw.community.model.network.response.DepartmentApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice/department")
public class DepartmentApiController extends CrudController<DepartmentApiRequest, DepartmentApiResponse, Department> {
}
