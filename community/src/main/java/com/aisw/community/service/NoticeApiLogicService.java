package com.aisw.community.service;

import com.aisw.community.controller.CrudController;
import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.Free;
import com.aisw.community.model.entity.Notice;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.NoticeApiRequest;
import com.aisw.community.model.network.response.FreeApiResponse;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.repository.NoticeRepository;
import com.aisw.community.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NoticeApiLogicService extends BaseService<NoticeApiRequest, NoticeApiResponse, Notice> {

    @Override
    public Header<NoticeApiResponse> create(Header<NoticeApiRequest> request) {
        NoticeApiRequest noticeApiRequest = request.getData();

        Notice notice = Notice.builder()
                .build();
        Notice newNotice = baseRepository.save(notice);

        return Header.OK(response(newNotice));
    }

    @Override
    public Header<NoticeApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<NoticeApiResponse> update(Header<NoticeApiRequest> request) {
//        NoticeApiRequest noticeApiRequest = request.getData();
//
//        return baseRepository.findById(noticeApiRequest.getId())
//                .map(notice -> {
//                    notice.setUser(userRepository.getOne(noticeApiRequest.getUserId()));
//                    return notice;
//                })
//                .map(notice -> baseRepository.save(notice))
//                .map(this::response)
//                .map(Header::OK)
//                .orElseGet(() -> Header.ERROR("데이터 없음"));
        return Header.ERROR("데이터 없음");
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(notice -> {
                    baseRepository.delete(notice);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private NoticeApiResponse response(Notice notice) {
        NoticeApiResponse noticeApiResponse = NoticeApiResponse.builder()
                .id(notice.getId())
                .university(notice.getUniversity())
                .department(notice.getDepartment())
                .council(notice.getCouncil())
                .build();

        return noticeApiResponse;
    }

    @Override
    public Header<List<NoticeApiResponse>> search(Pageable pageable) {
        Page<Notice> notices = baseRepository.findAll(pageable);

        List<NoticeApiResponse> noticeApiResponseList = notices.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(noticeApiResponseList);
    }
}
