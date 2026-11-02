package com.project.creditmanagementbackend.app.util.pagination.util;

import com.project.creditmanagementbackend.app.util.pagination.model.PageRequestDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public final class PageableFactory {

    private PageableFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static Pageable create(PageRequestDto dto) {
        Sort sort = Sort.unsorted();

        if (dto.getSort() != null && !dto.getSort().isBlank()) {
            Sort.Direction direction = Optional.ofNullable(dto.getDirection())
                    .filter(d -> d.equalsIgnoreCase("desc"))
                    .map(d -> Sort.Direction.DESC)
                    .orElse(Sort.Direction.ASC);

            sort = Sort.by(direction, dto.getSort());
        }

        int pageNumber = Math.max(dto.getPage() - 1, 0);
        return PageRequest.of(pageNumber, dto.getPerPage(), sort);}
}