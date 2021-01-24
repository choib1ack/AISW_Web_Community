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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeApiLogicService extends BaseService<NoticeApiRequest, NoticeApiResponse, Notice> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<NoticeApiResponse> create(Header<NoticeApiRequest> request) {
        NoticeApiRequest noticeApiRequest = request.getData();

        Notice notice = Notice.builder()
                .user(userRepository.getOne(noticeApiRequest.getUserId()))
                .build();
        Notice newNotice = baseRepository.save(notice);

        return response(newNotice);
    }

    @Override
    public Header<NoticeApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<NoticeApiResponse> update(Header<NoticeApiRequest> request) {
        NoticeApiRequest noticeApiRequest = request.getData();

        return baseRepository.findById(noticeApiRequest.getId())
                .map(notice -> {
                    notice.setUser(userRepository.getOne(noticeApiRequest.getUserId()));
                    return notice;
                })
                .map(notice -> baseRepository.save(notice))
                .map(this::response)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
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

    private Header<NoticeApiResponse> response(Notice notice) {
        NoticeApiResponse noticeApiResponse = NoticeApiResponse.builder()
                .id(notice.getId())
                .userId(notice.getUser().getId())
                .build();

        return Header.OK(noticeApiResponse);
    }
}
