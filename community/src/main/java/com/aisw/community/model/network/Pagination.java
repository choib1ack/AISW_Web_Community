package com.aisw.community.model.network;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {

    private Integer totalPages;

    private Long totalElements;

    private Integer currentPage;

    private Integer currentElements;
}
