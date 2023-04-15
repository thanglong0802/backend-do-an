package com.api.base.service;

import java.io.File;
import java.util.List;

import com.api.base.domain.PagingResponse;
import org.springframework.data.domain.Pageable;

import com.api.base.domain.sample.SampleCreateRequest;
import com.api.base.domain.sample.SampleDetailResponse;
import com.api.base.domain.sample.SampleRequest;
import com.api.base.domain.sample.SampleResponse;
import com.api.base.domain.sample.SampleUpdateRequest;

public interface SampleService {

    PagingResponse search(SampleRequest request, Pageable pageRequest);

    SampleResponse insert(SampleCreateRequest request);

    SampleResponse update(SampleUpdateRequest request);

    SampleDetailResponse detail(Long id);

    Boolean delete(Long id);

    Boolean deleteAll(List<Long> ids);

    File export(SampleRequest request, Pageable pageRequest) throws Exception;
}
