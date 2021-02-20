package com.aisw.community.service;

import com.aisw.community.model.entity.Notice;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.NoticeApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeApiLogicService extends BoardService<NoticeApiResponse, Notice> {

    @Override
    public Header<List<NoticeApiResponse>> searchList(Pageable pageable) {
        return null;
    }

    @Override
    public Header<List<NoticeApiResponse>> searchByWriter(String writer, Pageable pageable) {
        return null;
    }

    @Override
    public Header<List<NoticeApiResponse>> searchByTitle(String title, Pageable pageable) {
        return null;
    }

    @Override
    public Header<List<NoticeApiResponse>> searchByTitleOrContent(String title, String content, Pageable pageable) {
        return null;
    }

    private NoticeApiResponse response(Notice notice) {
        NoticeApiResponse noticeApiResponse = NoticeApiResponse.builder()
                .build();

        return noticeApiResponse;
    }
}
