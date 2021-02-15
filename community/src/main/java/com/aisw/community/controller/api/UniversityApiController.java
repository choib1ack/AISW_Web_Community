package com.aisw.community.controller.api;

import com.aisw.community.controller.CrudController;
import com.aisw.community.controller.PostController;
import com.aisw.community.model.entity.University;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.UniversityApiRequest;
import com.aisw.community.model.network.response.UniversityApiResponse;
import com.aisw.community.service.UniversityApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notice/university")
public class UniversityApiController extends PostController<UniversityApiRequest, UniversityApiResponse, University> {
}
