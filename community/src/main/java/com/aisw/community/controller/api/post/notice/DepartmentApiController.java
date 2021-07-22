package com.aisw.community.controller.api.post.notice;

import com.aisw.community.model.entity.post.notice.Department;
import com.aisw.community.model.network.request.post.notice.DepartmentApiRequest;
import com.aisw.community.model.network.request.post.notice.FileUploadToDepartmentDTO;
import com.aisw.community.model.network.response.post.notice.DepartmentApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notice/department")
public class DepartmentApiController extends NoticePostController<DepartmentApiRequest, FileUploadToDepartmentDTO, NoticeResponseDTO, DepartmentApiResponse, Department> {
}
