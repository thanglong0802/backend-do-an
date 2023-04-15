package com.api.base.service.impl;

import com.api.base.utils.MessageUtil;
import com.api.base.utils.enumerate.MessageCode;
import com.api.base.domain.PagingResponse;
import com.api.base.domain.sample.SampleCreateRequest;
import com.api.base.domain.sample.SampleDetailResponse;
import com.api.base.domain.sample.SampleExportResponse;
import com.api.base.domain.sample.SampleRequest;
import com.api.base.domain.sample.SampleResponse;
import com.api.base.domain.sample.SampleUpdateRequest;
import com.api.base.entity.Sample;
import com.api.base.exception.BusinessException;
import com.api.base.repository.SampleRepository;
import com.api.base.service.CommonService;
import com.api.base.service.SampleService;
import com.api.base.utils.SimpleQueryBuilder;
import com.api.base.utils.Utilities;
import com.api.base.utils.helper.ExcelHelper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
@Transactional
public class SampleServiceImpl implements SampleService {
    @Value("${export.excel}")
    private String pathExportExcel;

    private final MessageUtil messageUtil;
    private final CommonService commonService;
    private final SampleRepository sampleRepository;

    public SampleServiceImpl(CommonService commonService, SampleRepository sampleRepository, MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
        this.commonService = commonService;
        this.sampleRepository = sampleRepository;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PagingResponse search(SampleRequest request, Pageable pageRequest) {
        StringBuilder whereClause = new StringBuilder("1 = 1");
        SimpleQueryBuilder simpleQueryBuilder = new SimpleQueryBuilder();
        Map<String, Object> params = new HashMap<>();
        whereClause.append(Utilities.buildWhereClause(request, params));

        simpleQueryBuilder.from("sample");
        simpleQueryBuilder.where(whereClause.toString());

        PagingResponse pagingRs = commonService.executeSearchData(pageRequest, simpleQueryBuilder, params, Sample.class);
        List<Sample> datas = (List<Sample>) pagingRs.getData();
        List<SampleResponse> caseResponses = Utilities.copyProperties(datas, SampleResponse.class);
        pagingRs.setData(caseResponses);
        return pagingRs;
    }

    @Override
    public SampleResponse insert(SampleCreateRequest request) {
        Sample sample = Utilities.copyProperties(request, Sample.class);
        sampleRepository.save(sample);
        return Utilities.copyProperties(sample, SampleResponse.class);
    }

    @Override
    public SampleResponse update(SampleUpdateRequest request) {
        Sample sample = getSampleById(request.getId());
        Utilities.updateProperties(request, sample);
        sampleRepository.save(sample);
        return Utilities.copyProperties(sample, SampleResponse.class);
    }

    private Sample getSampleById (Long id) {
        Sample sample = Utilities.returnNullInException(() -> sampleRepository.findById(id).get());
        if (ObjectUtils.allNull(sample)) {
            throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.BASE_01001.name()), "Sample id: " + id);
        }
        return sample;
    }

    @Override
    public SampleDetailResponse detail(Long id) {
        Sample sample = getSampleById(id);
        return Utilities.copyProperties(sample, SampleDetailResponse.class);
    }

    @Override
    public Boolean delete(Long id) {
        try {
            sampleRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteAll(List<Long> ids) {
        try {
            List<Sample> samples = sampleRepository.findAllById(ids);
            if (ids.size() != samples.size()) {
                throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.ERR_404.name()), "Sample ids: " + ids);
            }
            sampleRepository.deleteAll(samples);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public File export(SampleRequest request, Pageable pageRequest) throws Exception {
        List<SampleResponse> sampleResponses = (List<SampleResponse>) search(request, pageRequest).getData();
        List<SampleExportResponse> dataExport = Utilities.copyProperties(sampleResponses, SampleExportResponse.class);
        String filePath = ExcelHelper.writeDataToExcel(dataExport, lsFields(), pathExportExcel);
        return new File(filePath);
    }

    private List<String> lsFields() {
        List<String> fields = new ArrayList<>();
        fields.add("id");
        fields.add("name");
        fields.add("birthDay");
        return fields;
    }
}
