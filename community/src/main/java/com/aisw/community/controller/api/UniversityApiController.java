package com.aisw.community.controller.api;

import com.aisw.community.controller.CrudController;
import com.aisw.community.model.entity.University;
import com.aisw.community.model.network.request.UniversityApiRequest;
import com.aisw.community.model.network.response.UniversityApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice/university")
public class UniversityApiController extends CrudController<UniversityApiRequest, UniversityApiResponse, University> {
}
