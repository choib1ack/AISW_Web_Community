package com.aisw.community.controller.api.post;

import com.aisw.community.model.entity.post.Bulletin;
import com.aisw.community.model.network.response.post.BulletinResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/bulletin")
public class BulletinApiController extends BulletinController<BulletinResponseDTO, Bulletin> {
}
