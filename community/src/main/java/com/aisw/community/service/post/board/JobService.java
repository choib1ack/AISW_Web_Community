package com.aisw.community.service.post.board;

import com.aisw.community.component.advice.exception.NotEqualUserException;
import com.aisw.community.component.advice.exception.PostNotFoundException;
import com.aisw.community.component.advice.exception.PostStatusNotSuitableException;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.entity.post.board.Job;
import com.aisw.community.model.entity.post.like.ContentLike;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.post.board.FileUploadToJobRequest;
import com.aisw.community.model.network.request.post.board.JobApiRequest;
import com.aisw.community.model.network.response.post.board.*;
import com.aisw.community.model.network.response.post.comment.CommentApiResponse;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.repository.post.board.JobRepository;
import com.aisw.community.repository.post.file.FileRepository;
import com.aisw.community.repository.post.like.ContentLikeRepository;
import com.aisw.community.service.post.comment.CommentService;
import com.aisw.community.service.post.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService extends BoardPostService<JobApiRequest, FileUploadToJobRequest, JobResponseDTO, JobDetailApiResponse, JobApiResponse, Job> {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ContentLikeRepository contentLikeRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FileService fileService;

    @Override
    @Caching(evict = {
            @CacheEvict(value = "jobReadAll", allEntries = true),
            @CacheEvict(value = "jobSearchByWriter", allEntries = true),
            @CacheEvict(value = "jobSearchByTitle", allEntries = true),
            @CacheEvict(value = "jobSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<JobApiResponse> create(Authentication authentication, Header<JobApiRequest> request) {
        JobApiRequest jobApiRequest = request.getData();
        if (jobApiRequest.getStatus().equals(BulletinStatus.URGENT)
                || jobApiRequest.getStatus().equals(BulletinStatus.NOTICE)) {
            throw new PostStatusNotSuitableException(jobApiRequest.getStatus().getTitle());
        }

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();

        Job job = Job.builder()
                .title(jobApiRequest.getTitle())
                .writer(user.getName())
                .content(jobApiRequest.getContent())
                .status(jobApiRequest.getStatus())
                .isAnonymous(jobApiRequest.getIsAnonymous())
                .firstCategory(FirstCategory.BOARD)
                .secondCategory(SecondCategory.JOB)
                .likes(0L)
                .user(user)
                .build();

        Job newJob = baseRepository.save(job);
        return Header.OK(response(newJob));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "jobReadAll", allEntries = true),
            @CacheEvict(value = "jobSearchByWriter", allEntries = true),
            @CacheEvict(value = "jobSearchByTitle", allEntries = true),
            @CacheEvict(value = "jobSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<JobApiResponse> create(Authentication authentication, FileUploadToJobRequest request) {
        JobApiRequest jobApiRequest = request.getJobApiRequest();
        if (jobApiRequest.getStatus().equals(BulletinStatus.URGENT)
                || jobApiRequest.getStatus().equals(BulletinStatus.NOTICE)) {
            throw new PostStatusNotSuitableException(jobApiRequest.getStatus().getTitle());
        }

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();

        Job job = Job.builder()
                .title(jobApiRequest.getTitle())
                .writer(user.getName())
                .content(jobApiRequest.getContent())
                .status(jobApiRequest.getStatus())
                .isAnonymous(jobApiRequest.getIsAnonymous())
                .firstCategory(FirstCategory.BOARD)
                .secondCategory(SecondCategory.JOB)
                .likes(0L)
                .user(user)
                .build();

        Job newJob = baseRepository.save(job);

        MultipartFile[] files = request.getFiles();
        List<FileApiResponse> fileApiResponseList = fileService.uploadFiles(files, newJob.getId(), UploadCategory.POST);

        return Header.OK(response(newJob, fileApiResponseList));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "jobReadAll", allEntries = true),
            @CacheEvict(value = "jobSearchByWriter", allEntries = true),
            @CacheEvict(value = "jobSearchByTitle", allEntries = true),
            @CacheEvict(value = "jobSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true)
    })
    public Header<JobApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(job -> job.setViews(job.getViews() + 1))
                .map(job -> baseRepository.save((Job) job))
                .map(this::response)
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "jobReadAll", allEntries = true),
            @CacheEvict(value = "jobSearchByWriter", allEntries = true),
            @CacheEvict(value = "jobSearchByTitle", allEntries = true),
            @CacheEvict(value = "jobSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<JobApiResponse> update(Authentication authentication, Header<JobApiRequest> request) {
        JobApiRequest jobApiRequest = request.getData();
        if (jobApiRequest.getStatus().equals(BulletinStatus.URGENT)
                || jobApiRequest.getStatus().equals(BulletinStatus.NOTICE)) {
            throw new PostStatusNotSuitableException(jobApiRequest.getStatus().getTitle());
        }

        Job job = baseRepository.findById(jobApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(jobApiRequest.getId()));

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        if (job.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        job
                .setTitle(jobApiRequest.getTitle())
                .setContent(jobApiRequest.getContent())
                .setStatus(jobApiRequest.getStatus());
        job.setIsAnonymous(jobApiRequest.getIsAnonymous());
        baseRepository.save(job);

        return Header.OK(response(job));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "jobReadAll", allEntries = true),
            @CacheEvict(value = "jobSearchByWriter", allEntries = true),
            @CacheEvict(value = "jobSearchByTitle", allEntries = true),
            @CacheEvict(value = "jobSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<JobApiResponse> update(Authentication authentication, FileUploadToJobRequest request) {
        JobApiRequest jobApiRequest = request.getJobApiRequest();
        if (jobApiRequest.getStatus().equals(BulletinStatus.URGENT)
                || jobApiRequest.getStatus().equals(BulletinStatus.NOTICE)) {
            throw new PostStatusNotSuitableException(jobApiRequest.getStatus().getTitle());
        }

        Job job = baseRepository.findById(jobApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(jobApiRequest.getId()));

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        if (job.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        MultipartFile[] files = request.getFiles();
        job.getFileList().stream().forEach(file -> fileRepository.delete(file));
        job.getFileList().clear();
        List<FileApiResponse> fileApiResponseList = fileService.uploadFiles(files, job.getId(), UploadCategory.POST);

        job
                .setTitle(jobApiRequest.getTitle())
                .setContent(jobApiRequest.getContent())
                .setStatus(jobApiRequest.getStatus());
        job.setIsAnonymous(jobApiRequest.getIsAnonymous());
        baseRepository.save(job);

        return Header.OK(response(job, fileApiResponseList));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "jobReadAll", allEntries = true),
            @CacheEvict(value = "jobSearchByWriter", allEntries = true),
            @CacheEvict(value = "jobSearchByTitle", allEntries = true),
            @CacheEvict(value = "jobSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header delete(Authentication authentication, Long id) {
        Job job = baseRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        if (job.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        baseRepository.delete(job);
        return Header.OK();
    }

    private JobApiResponse response(Job job) {
        JobApiResponse jobApiResponse = JobApiResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .writer(job.getWriter())
                .content(job.getContent())
                .status(job.getStatus())
                .createdAt(job.getCreatedAt())
                .createdBy(job.getCreatedBy())
                .updatedAt(job.getUpdatedAt())
                .updatedBy(job.getUpdatedBy())
                .views(job.getViews())
                .likes(job.getLikes())
                .isAnonymous(job.getIsAnonymous())
                .category(job.getCategory())
                .build();
        if (job.getFileList() != null) {
            jobApiResponse.setFileApiResponseList(fileService.getFileList(job.getFileList(), UploadCategory.POST, job.getId()));
        }

        return jobApiResponse;
    }

    private JobApiResponse response(Job job, List<FileApiResponse> fileApiResponseList) {
        JobApiResponse jobApiResponse = JobApiResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .writer(job.getWriter())
                .content(job.getContent())
                .status(job.getStatus())
                .createdAt(job.getCreatedAt())
                .createdBy(job.getCreatedBy())
                .updatedAt(job.getUpdatedAt())
                .updatedBy(job.getUpdatedBy())
                .views(job.getViews())
                .likes(job.getLikes())
                .isAnonymous(job.getIsAnonymous())
                .category(job.getCategory())
                .fileApiResponseList(fileApiResponseList)
                .build();

        return jobApiResponse;
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "jobReadAll", allEntries = true),
            @CacheEvict(value = "jobSearchByWriter", allEntries = true),
            @CacheEvict(value = "jobSearchByTitle", allEntries = true),
            @CacheEvict(value = "jobSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true)
    })
    public Header<JobDetailApiResponse> readWithComment(Long id) {
        return baseRepository.findById(id)
                .map(job -> (Job) job.setViews(job.getViews() + 1))
                .map(this::responseWithComment)
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    private JobDetailApiResponse responseWithComment(Job job) {
        JobDetailApiResponse jobDetailApiResponse = JobDetailApiResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .writer(job.getWriter())
                .content(job.getContent())
                .status(job.getStatus())
                .createdAt(job.getCreatedAt())
                .createdBy(job.getCreatedBy())
                .updatedAt(job.getUpdatedAt())
                .updatedBy(job.getUpdatedBy())
                .views(job.getViews())
                .likes(job.getLikes())
                .isAnonymous(job.getIsAnonymous())
                .category(job.getCategory())
                .userId(job.getUser().getId())
                .checkLike(false)
                .fileApiResponseList(fileService.getFileList(job.getFileList(), UploadCategory.POST, job.getId()))
                .commentApiResponseList(commentService.searchByPost(job.getId()))
                .build();

        return jobDetailApiResponse;
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "jobReadAll", allEntries = true),
            @CacheEvict(value = "jobSearchByWriter", allEntries = true),
            @CacheEvict(value = "jobSearchByTitle", allEntries = true),
            @CacheEvict(value = "jobSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true)
    })
    public Header<JobDetailApiResponse> readWithCommentAndLike(Authentication authentication, Long id) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return baseRepository.findById(id)
                .map(job -> job.setViews(job.getViews() + 1))
                .map(job -> baseRepository.save((Job) job))
                .map(job -> responseWithCommentAndLike(principal.getUser(), job))
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    private JobDetailApiResponse responseWithCommentAndLike(User user, Job job) {
        List<CommentApiResponse> commentApiResponseList = commentService.searchByPost(job.getId());

        JobDetailApiResponse jobDetailApiResponse = JobDetailApiResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .writer(job.getWriter())
                .content(job.getContent())
                .status(job.getStatus())
                .createdAt(job.getCreatedAt())
                .createdBy(job.getCreatedBy())
                .updatedAt(job.getUpdatedAt())
                .updatedBy(job.getUpdatedBy())
                .views(job.getViews())
                .likes(job.getLikes())
                .isAnonymous(job.getIsAnonymous())
                .category(job.getCategory())
                .checkLike(false)
                .userId(job.getUser().getId())
                .fileApiResponseList(fileService.getFileList(job.getFileList(), UploadCategory.POST, job.getId()))
                .commentApiResponseList(commentApiResponseList)
                .build();

        List<ContentLike> contentLikeList = contentLikeRepository.findAllByUserId(user.getId());
        contentLikeList.stream().forEach(contentLike -> {
            if (contentLike.getBoard() != null) {
                if (contentLike.getBoard().getId() == job.getId()) {
                    jobDetailApiResponse.setCheckLike(true);
                }
            } else if (contentLike.getComment() != null) {
                for (CommentApiResponse commentApiResponse : commentApiResponseList) {
                    if (contentLike.getComment().getId() == commentApiResponse.getId()) {
                        commentApiResponse.setCheckLike(true);
                    } else {
                        for (CommentApiResponse subCommentApiResponse : commentApiResponse.getSubComment()) {
                            if (contentLike.getComment().getId() == subCommentApiResponse.getId()) {
                                subCommentApiResponse.setCheckLike(true);
                            }
                        }
                    }
                }
            }
        });
        jobDetailApiResponse.setCommentApiResponseList(commentApiResponseList);

        return jobDetailApiResponse;
    }


    @Override
    @Cacheable(value = "jobReadAll", key = "#pageable.pageNumber")
    public Header<JobResponseDTO> readAll(Pageable pageable) {
        Page<Job> jobs = baseRepository.findAll(pageable);
        Page<Job> freesByStatus = searchByStatus(pageable);

        return getListHeader(jobs, freesByStatus);
    }

    @Override
    @Cacheable(value = "jobSearchByWriter",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#writer, #pageable.pageNumber)")
    public Header<JobResponseDTO> searchByWriter(String writer, Pageable pageable) {
        Page<Job> jobs = jobRepository.findAllByWriterContaining(writer, pageable);
        Page<Job> jobsByStatus = searchByStatus(pageable);

        return getListHeader(jobs, jobsByStatus);
    }

    @Override
    @Cacheable(value = "jobSearchByTitle",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#title, #pageable.pageNumber)")
    public Header<JobResponseDTO> searchByTitle(String title, Pageable pageable) {
        Page<Job> jobs = jobRepository.findAllByTitleContaining(title, pageable);
        Page<Job> jobsByStatus = searchByStatus(pageable);

        return getListHeader(jobs, jobsByStatus);
    }

    @Override
    @Cacheable(value = "jobSearchByTitleOrContent",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#title, #content, #pageable.pageNumber)")
    public Header<JobResponseDTO> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Job> jobs = jobRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);
        Page<Job> jobsByStatus = searchByStatus(pageable);

        return getListHeader(jobs, jobsByStatus);
    }

    public Page<Job> searchByStatus(Pageable pageable) {
        Page<Job> jobs = jobRepository.findAllByStatus(BulletinStatus.REVIEW, pageable);

        return jobs;
    }

    private Header<JobResponseDTO> getListHeader(Page<Job> jobs, Page<Job> jobsByStatus) {
        JobResponseDTO jobResponseDTO = JobResponseDTO.builder()
                .boardApiResponseList(jobs.stream()
                        .map(job -> BoardApiResponse.builder()
                                .id(job.getId())
                                .title(job.getTitle())
                                .category(job.getCategory())
                                .createdAt(job.getCreatedAt())
                                .status(job.getStatus())
                                .views(job.getViews())
                                .writer(job.getWriter())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        jobResponseDTO.setBoardApiReviewResponseList(jobsByStatus.stream()
                .map(job -> BoardApiResponse.builder()
                        .id(job.getId())
                        .title(job.getTitle())
                        .category(job.getCategory())
                        .createdAt(job.getCreatedAt())
                        .status(job.getStatus())
                        .views(job.getViews())
                        .writer(job.getWriter())
                        .build())
                .collect(Collectors.toList()));

        Pagination pagination = Pagination.builder()
                .totalElements(jobs.getTotalElements())
                .totalPages(jobs.getTotalPages())
                .currentElements(jobs.getNumberOfElements())
                .currentPage(jobs.getNumber())
                .build();

        return Header.OK(jobResponseDTO, pagination);
    }
}