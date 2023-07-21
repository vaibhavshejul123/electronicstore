package com.bikkadit.electronic.store.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PageableResponse<T> {

    private List<T> content;

    private int pageNumber;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean lastPage;

}
