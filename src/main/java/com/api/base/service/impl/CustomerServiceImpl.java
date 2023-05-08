package com.api.base.service.impl;

import com.api.base.domain.PagingResponse;
import com.api.base.domain.customer.CustomerCreateRequest;
import com.api.base.domain.customer.CustomerRequest;
import com.api.base.domain.customer.CustomerResponse;
import com.api.base.domain.customer.CustomerUpdateRequest;
import com.api.base.entity.Customer;
import com.api.base.exception.BusinessException;
import com.api.base.repository.CustomerRepository;
import com.api.base.service.CommonService;
import com.api.base.service.CustomerService;
import com.api.base.utils.MessageUtil;
import com.api.base.utils.SimpleQueryBuilder;
import com.api.base.utils.Utilities;
import com.api.base.utils.enumerate.MessageCode;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CommonService commonService;

    private final MessageUtil messageUtil;

    public CustomerServiceImpl(CustomerRepository customerRepository, CommonService commonService, MessageUtil messageUtil) {
        this.customerRepository = customerRepository;
        this.commonService = commonService;
        this.messageUtil = messageUtil;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PagingResponse search(CustomerRequest request, Pageable pageRequest) {
        StringBuilder whereClause = new StringBuilder("1 = 1");
        SimpleQueryBuilder simpleQueryBuilder = new SimpleQueryBuilder();
        Map<String, Object> params = new HashMap<>();
        whereClause.append(Utilities.buildWhereClause(request, params));

        simpleQueryBuilder.from("tbl_customer");
        simpleQueryBuilder.where(whereClause.toString());

        PagingResponse pagingRs = commonService.executeSearchData(pageRequest, simpleQueryBuilder, params, Customer.class);
        List<Customer> datas = (List<Customer>) pagingRs.getData();
        List<CustomerResponse> caseResponses = Utilities.copyProperties(datas, CustomerResponse.class);
        pagingRs.setData(caseResponses);
        return pagingRs;
    }

    @Override
    public CustomerResponse insert(CustomerCreateRequest request) {
        Customer customer = Utilities.copyProperties(request, Customer.class);
        customerRepository.save(customer);
        return Utilities.copyProperties(customer, CustomerResponse.class);
    }

    @Override
    public CustomerResponse update(CustomerUpdateRequest request) {
        Customer customer = Utilities.returnNullInException(() -> customerRepository.findById(request.getId()).get());
        if (ObjectUtils.allNull(customer)) {
            throw new BusinessException(MessageCode.ERR_404.name(), messageUtil.getMessage(MessageCode.BASE_08001.name()), "Customer ID: " + request.getId());
        }
        Utilities.updateProperties(request, customer);
        customerRepository.save(customer);
        return Utilities.copyProperties(customer, CustomerResponse.class);
    }

    @Override
    public Boolean delete(Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
