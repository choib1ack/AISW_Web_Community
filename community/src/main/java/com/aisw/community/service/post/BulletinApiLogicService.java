package com.aisw.community.service.post;

import com.aisw.community.model.entity.post.Bulletin;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.response.post.BulletinApiResponse;
import com.aisw.community.model.network.response.post.BulletinResponseDTO;
import com.aisw.community.repository.post.BulletinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BulletinApiLogicService extends BulletinService<BulletinResponseDTO, Bulletin> {

    @Autowired
    private BulletinRepository bulletinRepository;

    @Override
    @Cacheable(value = "bulletinSearchByWriter",
            key = "T(com.aisw.community.util.KeyCreatorBean).createKey(#writer, #pageable.pageNumber)")
    public Header<BulletinResponseDTO> searchByWriter(String writer, Pageable pageable) {
        Page<Bulletin> bulletins = bulletinRepository.findAllByWriterContaining(writer, pageable);
        Page<Bulletin> bulletinsByStatus = searchByStatus(pageable);

        return getListHeader(bulletins, bulletinsByStatus);
    }

    @Override
    @Cacheable(value = "bulletinSearchByTitle",
            key = "T(com.aisw.community.util.KeyCreatorBean).createKey(#title, #pageable.pageNumber)")
    public Header<BulletinResponseDTO> searchByTitle(String title, Pageable pageable) {
        Page<Bulletin> bulletins = bulletinRepository.findAllByTitleContaining(title, pageable);
        Page<Bulletin> bulletinsByStatus = searchByStatus(pageable);

        return getListHeader(bulletins, bulletinsByStatus);
    }

    @Override
    @Cacheable(value = "bulletinSearchByTitleOrContent",
            key = "T(com.aisw.community.util.KeyCreatorBean).createKey(#title, #content, #pageable.pageNumber)")
    public Header<BulletinResponseDTO> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Bulletin> bulletins = bulletinRepository.findAllByTitleContainingOrContentContaining(title, content, pageable);
        Page<Bulletin> bulletinsByStatus = searchByStatus(pageable);

        return getListHeader(bulletins, bulletinsByStatus);
    }

    public Page<Bulletin> searchByStatus(Pageable pageable) {
        Page<Bulletin> bulletins = bulletinRepository.findAllByStatusOrStatus(
                BulletinStatus.URGENT, BulletinStatus.NOTICE, pageable);

        return bulletins;
    }

    private Header<BulletinResponseDTO> getListHeader
            (Page<Bulletin> bulletins, Page<Bulletin> bulletinsByStatus) {
        BulletinResponseDTO bulletinResponseDTO = BulletinResponseDTO.builder()
                .bulletinApiResponseList(bulletins.stream()
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
                        .collect(Collectors.toList()))
                .build();
        List<BulletinApiResponse> bulletinApiNoticeResponseList = new ArrayList<>();
        List<BulletinApiResponse> bulletinApiUrgentResponseList = new ArrayList<>();
        bulletinsByStatus.stream().forEach(bulletin -> {
            BulletinApiResponse bulletinApiResponse = BulletinApiResponse.builder()
                    .id(bulletin.getId())
                    .title(bulletin.getTitle())
                    .firstCategory(bulletin.getFirstCategory())
                    .secondCategory(bulletin.getSecondCategory())
                    .createdAt(bulletin.getCreatedAt())
                    .status(bulletin.getStatus())
                    .views(bulletin.getViews())
                    .writer(bulletin.getWriter())
                    .build();
            if(bulletinApiResponse.getStatus() == BulletinStatus.NOTICE) {
                bulletinApiNoticeResponseList.add(bulletinApiResponse);
            }
            else if(bulletinApiResponse.getStatus() == BulletinStatus.URGENT) {
                bulletinApiUrgentResponseList.add(bulletinApiResponse);
            }
        });
        bulletinResponseDTO.setBulletinApiNoticeResponseList(bulletinApiNoticeResponseList);
        bulletinResponseDTO.setBulletinApiUrgentResponseList(bulletinApiUrgentResponseList);

        Pagination pagination = Pagination.builder()
                .totalElements(bulletins.getTotalElements())
                .totalPages(bulletins.getTotalPages())
                .currentElements(bulletins.getNumberOfElements())
                .currentPage(bulletins.getNumber())
                .build();

        return Header.OK(bulletinResponseDTO, pagination);
    }
}
