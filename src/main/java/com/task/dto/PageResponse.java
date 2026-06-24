package com.task.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Paginated response wrapper")
public class PageResponse<T> {

    @Schema(description = "Page content")
    private List<T> content;

    @Schema(description = "Current page index (0-based)", example = "0")
    private int page;

    @Schema(description = "Page size", example = "20")
    private int size;

    @Schema(description = "Total number of elements", example = "42")
    private long totalElements;

    @Schema(description = "Total number of pages", example = "3")
    private int totalPages;

    @Schema(description = "Whether this is the last page", example = "false")
    private boolean last;
}
