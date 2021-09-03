package com.aisw.community.service.post.board;

import com.aisw.community.component.advice.exception.NotEqualUserException;
import com.aisw.community.component.advice.exception.PostNotFoundException;
import com.aisw.community.model.entity.post.board.Job;
import com.aisw.community.model.entity.post.file.File;
import com.aisw.community.model.entity.post.like.ContentLike;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.post.board.JobApiRequest;
import com.aisw.community.model.network.response.post.board.BoardApiResponse;
import com.aisw.community.model.network.response.post.board.JobApiResponse;
import com.aisw.community.model.network.response.post.board.JobDetailApiResponse;
import com.aisw.community.model.network.response.post.board.JobResponseDTO;
import com.aisw.community.model.network.response.post.comment.CommentApiResponse;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.repository.post.board.JobRepository;
import com.aisw.community.service.post.comment.CommentService;
import com.aisw.community.service.post.file.FileService;
import com.aisw.community.service.post.like.ContentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService implements BoardPostService<JobApiRequest, JobApiResponse, JobResponseDTO, JobDetailApiResponse> {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ContentLikeService contentLikeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FileService fileService;

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
    public Header<JobApiResponse> create(User user, JobApiRequest jobApiRequest, MultipartFile[] files) {
        Job job = Job.builder()
                .title(jobApiRequest.getTitle())
                .writer((jobApiRequest.getIsAnonymous() == true) ? "익명" : user.getName())
                .content(jobApiRequest.getContent())
                .status(jobApiRequest.getStatus())
                .firstCategory(FirstCategory.BOARD)
                .secondCategory(SecondCategory.JOB)
                .likes(0L)
                .user(user)
                .build();
        Job newJob = jobRepository.save(job);
        if (files != null) {
            List<FileApiResponse> fileApiResponseList =
                    fileService.uploadFiles(files, user.getUsername(), "/board/job", newJob.getId(), UploadCategory.POST);

            return Header.OK(response(newJob, fileApiResponseList));
        } else {
            return Header.OK(response(newJob));
        }
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
    public Header<JobDetailApiResponse> read(Long id) {
        return jobRepository.findById(id)
                .map(job -> job.setViews(job.getViews() + 1))
                .map(job -> jobRepository.save((Job) job))
                .map(job -> responseWithComment(job))
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
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
    public Header<JobDetailApiResponse> read(User user, Long id) {
        return jobRepository.findById(id)
                .map(job -> job.setViews(job.getViews() + 1))
                .map(job -> jobRepository.save((Job) job))
                .map(job -> responseWithCommentAndLike(user, job))
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
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
    public Header<JobApiResponse> update(User user, JobApiRequest jobApiRequest, MultipartFile[] files, List<Long> delFileIdList) {
        Job job = jobRepository.findById(jobApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(jobApiRequest.getId()));
        if (job.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        job
                .setWriter((jobApiRequest.getIsAnonymous() == true) ? "익명" : user.getName())
                .setTitle(jobApiRequest.getTitle())
                .setContent(jobApiRequest.getContent())
                .setStatus(jobApiRequest.getStatus());
        jobRepository.save(job);

        if(job.getFileList() != null && delFileIdList != null) {
            List<File> delFileList = new ArrayList<>();
            for(File file : job.getFileList()) {
                if(delFileIdList.contains(file.getId())) {
                    fileService.deleteFile(file);
                    delFileList.add(file);
                }
            }
            for (File file : delFileList) {
                job.getFileList().remove(file);
            }
        }
        if(files != null) {
            List<FileApiResponse> fileApiResponseList =
                    fileService.uploadFiles(files, user.getUsername(), "/board/job", job.getId(), UploadCategory.POST);
            return Header.OK(response(job, fileApiResponseList));
        } else {
            return Header.OK(response(job));
        }
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
    public Header delete(User user, Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        if (job.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        fileService.deleteFileList(job.getFileList());
        jobRepository.delete(job);
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
                .category(job.getCategory())
                .build();
        if (job.getFileList() != null) {
            jobApiResponse.setFileApiResponseList(fileService.getFileList(job.getFileList()));
        }

        return jobApiResponse;
    }

    private JobApiResponse response(Job job, List<FileApiResponse> fileApiResponseList) {
        if(job.getFileList() != null) {
            fileApiResponseList.addAll(fileService.getFileList(job.getFileList()));
        }
        return JobApiResponse.builder()
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
                .category(job.getCategory())
                .fileApiResponseList(fileApiResponseList)
                .build();
    }

    private JobDetailApiResponse responseWithComment(Job job) {
        return JobDetailApiResponse.builder()
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
                .category(job.getCategory())
                .userId(job.getUser().getId())
                .checkLike(false)
                .fileApiResponseList(fileService.getFileList(job.getFileList()))
                .commentApiResponseList(commentService.searchByPost(job.getId()))
                .build();
    }

    private JobDetailApiResponse responseWithCommentAndLike(User user, Job job) {
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
                .category(job.getCategory())
                .checkLike(false)
                .userId(job.getUser().getId())
                .fileApiResponseList(fileService.getFileList(job.getFileList()))
                .build();

        List<CommentApiResponse> commentApiResponseList = commentService.searchByPost(user, job.getId());
        List<ContentLike> contentLikeList = contentLikeService.getContentLikeByUser(user.getId());
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
        jobDetailApiResponse.setIsWriter((user.getId() == job.getUser().getId()) ? true : false);

        return jobDetailApiResponse;
    }


    @Override
    @Cacheable(value = "jobReadAll", key = "#pageable.pageNumber")
    public Header<JobResponseDTO> readAll(Pageable pageable) {
        Page<Job> jobs = jobRepository.findAll(pageable);
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
