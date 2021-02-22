package com.aisw.community.service;

import com.aisw.community.model.entity.Board;
import com.aisw.community.model.entity.Bulletin;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.response.BulletinApiResponse;
import com.aisw.community.repository.BulletinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BulletinApiLogicService extends BulletinService<BulletinApiResponse, Bulletin> {

    @Autowired
    private BulletinRepository bulletinRepository;

    @Override
    public Header<List<BulletinApiResponse>> searchByWriter(String writer, Pageable pageable) {
        Page<Bulletin> bulletins = bulletinRepository.findAllByWriterContaining(writer, pageable);

        return getListHeader(bulletins);
    }

    @Override
    public Header<List<BulletinApiResponse>> searchByTitle(String title, Pageable pageable) {
        Page<Bulletin> bulletins = bulletinRepository.findAllByTitleContaining(title, pageable);

        return getListHeader(bulletins);
    }

    @Override
    public Header<List<BulletinApiResponse>> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Bulletin> bulletins = bulletinRepository.findAllByTitleContainingOrContentContaining(title, content, pageable);

        return getListHeader(bulletins);
    }

    private Header<List<BulletinApiResponse>> getListHeader(Page<Bulletin> bulletins) {
        List<BulletinApiResponse> bulletinApiResponseList = bulletins.stream()
                .map(bulletin -> BulletinApiResponse.builder()
                        .id(bulletin.getId())
                        .title(bulletin.getTitle())
                        .firstCategory(bulletin.getFirstCategory())
                        .secondCategory(bulletin.getSecondCategory())
                        .createdAt(bulletin.getCreatedAt())
                        .status(bulletin.getStatus())
                        .views(bulletin.getViews())
                        .writer(bulletin.getWriter())
                        .build())
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(bulletins.getTotalElements())
                .totalPages(bulletins.getTotalPages())
                .currentElements(bulletins.getNumberOfElements())
                .currentPage(bulletins.getNumber())
                .build();

        return Header.OK(bulletinApiResponseList, pagination);
    }
}
