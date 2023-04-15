package com.api.base.service.impl;

import com.api.base.domain.PagingResponse;
import com.api.base.service.CommonService;
import com.api.base.utils.DateTimeUtils;
import com.api.base.utils.SimpleQueryBuilder;
import com.api.base.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private EntityManager entityManager;

    @Override
    public <T> PagingResponse executeSearchData(Pageable pageRequest, SimpleQueryBuilder simpleQueryBuilder, Map<String, Object> params, Class<T> clazz) {
        // add Sorts
        pageRequest.getSort().forEach(s -> simpleQueryBuilder.orderBy(s.getProperty(), s.getDirection()));
        simpleQueryBuilder.offset(pageRequest.getPageNumber());
        simpleQueryBuilder.limit(pageRequest.getPageSize());

        Query selectQuery = entityManager.createNativeQuery(simpleQueryBuilder.build(), clazz);
        if (!params.isEmpty()) {
            for (Entry<String, Object> entry : params.entrySet()) {
                Object val = entry.getValue();
                if (entry.getValue().getClass().equals(Instant.class)) {
                    val = DateTimeUtils.convertInstantToTimestamp((Instant) entry.getValue());
                }
                selectQuery.setParameter(entry.getKey(), val);
            }
        }

        // convert to DTO
        return new PagingResponse(selectQuery.getResultList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T executeGetDetail(SimpleQueryBuilder simpleQueryBuilder, Map<String, Object> params, Class<T> clazz) {
        Query selectQuery = entityManager.createNativeQuery(simpleQueryBuilder.build());
        if (!params.isEmpty()) {
            for (Entry<String, Object> entry : params.entrySet()) {
                Object val = entry.getValue();
                if (entry.getValue().getClass().equals(Instant.class)) {
                    val = DateTimeUtils.convertInstantToTimestamp((Instant) entry.getValue());
                }
                selectQuery.setParameter(entry.getKey(), val);
            }
        }
        return Utilities.returnNullInException(() -> (T) selectQuery.getSingleResult());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tuple> executeGetListTuple(String sql, Map<String, Object> params) {
        Query selectQuery = entityManager.createNativeQuery(sql, Tuple.class);
        if (!params.isEmpty()) {
            for (Entry<String, Object> entry : params.entrySet()) {
                Object val = entry.getValue();
                if (entry.getValue().getClass().equals(Instant.class)) {
                    val = DateTimeUtils.convertInstantToTimestamp((Instant) entry.getValue());
                }
                selectQuery.setParameter(entry.getKey(), val);
            }
        }
        return Utilities.returnNullInException(selectQuery::getResultList);
    }

    @Override
    public Tuple executeGetOneTuple(String sql, Map<String, Object> params) {
        List<Tuple> tuples = executeGetListTuple(sql, params);
        return Utilities.returnNullInException(() -> tuples.stream().findFirst().get());
    }

    @Override
    public String getSortProperty(Sort sort) {
        String property = "";
        for (Sort.Order order : sort) {
            property = order.getProperty();
        }
        return property;
    }

    @Override
    public String getSortPropertyDirection(Sort sort) {
        String direction = "";
        for (Sort.Order order : sort) {
            direction = order.getDirection().name();
        }
        return direction;
    }
}
