package com.aisw.community.service.admin;

import com.aisw.community.component.advice.exception.BannerNotFoundException;
import com.aisw.community.component.advice.exception.FaqNotFoundException;
import com.aisw.community.model.entity.admin.Banner;
import com.aisw.community.model.entity.admin.Faq;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.admin.BannerApiRequest;
import com.aisw.community.model.network.request.admin.FaqApiRequest;
import com.aisw.community.model.network.request.admin.FileUploadToBannerRequest;
import com.aisw.community.model.network.response.admin.BannerApiResponse;
import com.aisw.community.model.network.response.admin.FaqApiResponse;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.repository.admin.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FaqService {

    @Autowired
    private FaqRepository faqRepository;

    public Header<FaqApiResponse> create(FaqApiRequest faqApiRequest) {
        Faq faq = Faq.builder()
                .question(faqApiRequest.getQuestion())
                .answer(faqApiRequest.getAnswer())
                .build();
        Faq newFaq = faqRepository.save(faq);

        return Header.OK(response(newFaq));
    }

    public Header<List<FaqApiResponse>> readAll() {
        List<Faq> faqList = faqRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<FaqApiResponse> faqApiResponseList = faqList.stream().map(this::response).collect(Collectors.toList());
        return Header.OK(faqApiResponseList);
    }

    public Header<FaqApiResponse> update(FaqApiRequest faqApiRequest) {
        Faq faq = faqRepository.findById(faqApiRequest.getId())
                .orElseThrow(() -> new FaqNotFoundException(faqApiRequest.getId()));

        faq
                .setQuestion(faqApiRequest.getQuestion())
                .setAnswer(faqApiRequest.getAnswer());
        Faq updatedFaq = faqRepository.save(faq);

        return Header.OK(response(updatedFaq));
    }

    public Header delete(Long id) {
        Faq faq = faqRepository.findById(id).orElseThrow(() -> new FaqNotFoundException(id));
        faqRepository.delete(faq);
        return Header.OK();
    }

    private FaqApiResponse response(Faq faq) {
        return FaqApiResponse.builder()
                .id(faq.getId())
                .question(faq.getQuestion())
                .answer(faq.getAnswer())
                .createdAt(faq.getCreatedAt())
                .createdBy(faq.getCreatedBy())
                .updatedAt(faq.getUpdatedAt())
                .updatedBy(faq.getUpdatedBy())
                .build();
    }
}
