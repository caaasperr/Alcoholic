package com.caaasperr.Alcoholic._common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Criteria {
    private static final int DEFAULT_PAGE = 0;
    private static final int MIN_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 100;
    private static final int MIN_PAGE_SIZE = 1;

    private final int page;
    private final int size;

    public static Criteria of(Integer page, Integer size) {
        return new Criteria(validateAndCorrectPage(page), validateAndCorrectSize(size));
    }

    public static Criteria of(Integer page, Integer size, Integer total) {
        return new Criteria(validateAndCorrectPage(page, size, total), validateAndCorrectSize(size));
    }

    private static int validateAndCorrectPage(Integer page) {
        if (page == null) {
            page = DEFAULT_PAGE;
        }

        if (page < MIN_PAGE) {
            page = MIN_PAGE;
        }

        return page;
    }

    private static int validateAndCorrectPage(Integer page, Integer size, Integer total) {
        if (page == null) page = DEFAULT_PAGE;
        if (size == null || size <= 0) size = DEFAULT_PAGE_SIZE;
        if (total == null || total < 0) total = 0;

        int totalPage = (total == 0) ? 1 : (int) Math.ceil((double) total / size);

        if (page < MIN_PAGE) {
            page = MIN_PAGE;
        } else if (page > totalPage) {
            page = totalPage;
        }

        return page;
    }

    private static int validateAndCorrectSize(Integer size) {
        if (size == null) {
            return DEFAULT_PAGE_SIZE;
        } else if (size < MIN_PAGE_SIZE) {
            return MIN_PAGE_SIZE;
        } else if (size > MAX_PAGE_SIZE) {
            return MAX_PAGE_SIZE;
        } else {
            return size;
        }
    }

}
