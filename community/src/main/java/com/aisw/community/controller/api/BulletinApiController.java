package com.aisw.community.controller.api;

import com.aisw.community.controller.BulletinController;
import com.aisw.community.model.entity.Bulletin;
import com.aisw.community.model.network.response.BulletinResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/bulletin")
public class BulletinApiController extends BulletinController<BulletinResponseDTO, Bulletin> {
}
