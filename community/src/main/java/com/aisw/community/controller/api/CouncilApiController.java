package com.aisw.community.controller.api;

import com.aisw.community.controller.CrudController;
import com.aisw.community.model.entity.Council;
import com.aisw.community.model.network.request.CouncilApiRequest;
import com.aisw.community.model.network.response.CouncilApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice/council")
public class CouncilApiController extends CrudController<CouncilApiRequest, CouncilApiResponse, Council> {
}
