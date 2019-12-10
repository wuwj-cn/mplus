/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mplus.common.utils.jpa;

import com.mplus.common.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * JpaUtils
 *
 * @author wuwj [254513235@qq.com]
 * @since 1.0
 */
public class JpaUtils<T extends BaseEntity> {
    public static <T> Specification<T> buildSpecification(List<QueryParam> params) {
        Specification<T> spec = (Specification<T>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            Predicate predicate = null;
            for (QueryParam param : params) {
                Object value = param.getValue();
                if (value == null || StringUtils.isBlank(value.toString())) {
                    continue;
                }
                predicate = getPredicate(param, root, cb);
                list.add(predicate);
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        };
        return spec;
    }

    private static <T> Predicate getPredicate(QueryParam param, Root<T> root, CriteriaBuilder cb) {
        switch (param.getQueryType()) {
            case eq:
                return cb.equal(root.get(param.getName()).as(param.getValue().getClass()), param.getValue());
            case like:
                return cb.like(root.get(param.getName()).as(String.class), String.format("%%%s%%", param.getValue()));
            case ne:
                return cb.notEqual(root.get(param.getName()).as(param.getValue().getClass()), param.getValue());
            case lt:
                return getLessThanPredicate(param, root, cb);
            case lte:
                return getLessThanOrEqualToPredicate(param, root, cb);
            case gt:
                return getGreaterThanPredicate(param, root, cb);
            case gte:
                return getGreaterThanOrEqualToPredicate(param, root, cb);
            default:
                throw new UnsupportedOperationException(String.format("不支持的查询类型[%s]", param.getQueryType().name()));
        }
    }

    private static <T> Predicate getLessThanPredicate(QueryParam param, Root<T> root, CriteriaBuilder cb) {
        if (Integer.class == param.getValue().getClass()) {
            return cb.lessThan(root.get(param.getName()).as(Integer.class), (int) param.getValue());
        }
        if (Long.class == param.getValue().getClass()) {
            return cb.lessThan(root.get(param.getName()).as(Long.class), (long) param.getValue());
        }
        if (Double.class == param.getValue().getClass()) {
            return cb.lessThan(root.get(param.getName()).as(Double.class), (double) param.getValue());
        }
        if (Float.class == param.getValue().getClass()) {
            return cb.lessThan(root.get(param.getName()).as(Float.class), (float) param.getValue());
        }
        if (Timestamp.class == param.getValue().getClass()) {
            return cb.lessThan(root.get(param.getName()).as(Timestamp.class), (Timestamp) param.getValue());
        }
        if (Date.class == param.getValue().getClass()) {
            return cb.lessThan(root.get(param.getName()).as(Date.class), (Date) param.getValue());
        }
        return cb.lessThan(root.get(param.getName()).as(String.class), String.valueOf(param.getValue()));
    }

    private static <T> Predicate getLessThanOrEqualToPredicate(QueryParam param, Root<T> root, CriteriaBuilder cb) {
        if (Integer.class == param.getValue().getClass()) {
            return cb.lessThanOrEqualTo(root.get(param.getName()).as(Integer.class), (int) param.getValue());
        }
        if (Long.class == param.getValue().getClass()) {
            return cb.lessThanOrEqualTo(root.get(param.getName()).as(Long.class), (long) param.getValue());
        }
        if (Double.class == param.getValue().getClass()) {
            return cb.lessThanOrEqualTo(root.get(param.getName()).as(Double.class), (double) param.getValue());
        }
        if (Float.class == param.getValue().getClass()) {
            return cb.lessThanOrEqualTo(root.get(param.getName()).as(Float.class), (float) param.getValue());
        }
        if (Timestamp.class == param.getValue().getClass()) {
            return cb.lessThanOrEqualTo(root.get(param.getName()).as(Timestamp.class), (Timestamp) param.getValue());
        }
        if (Date.class == param.getValue().getClass()) {
            return cb.lessThanOrEqualTo(root.get(param.getName()).as(Date.class), (Date) param.getValue());
        }
        return cb.lessThanOrEqualTo(root.get(param.getName()).as(String.class), String.valueOf(param.getValue()));
    }

    private static <T> Predicate getGreaterThanPredicate(QueryParam param, Root<T> root, CriteriaBuilder cb) {
        if (Integer.class == param.getValue().getClass()) {
            return cb.greaterThan(root.get(param.getName()).as(Integer.class), (int) param.getValue());
        }
        if (Long.class == param.getValue().getClass()) {
            return cb.greaterThan(root.get(param.getName()).as(Long.class), (long) param.getValue());
        }
        if (Double.class == param.getValue().getClass()) {
            return cb.greaterThan(root.get(param.getName()).as(Double.class), (double) param.getValue());
        }
        if (Float.class == param.getValue().getClass()) {
            return cb.greaterThan(root.get(param.getName()).as(Float.class), (float) param.getValue());
        }
        if (Timestamp.class == param.getValue().getClass()) {
            return cb.greaterThan(root.get(param.getName()).as(Timestamp.class), (Timestamp) param.getValue());
        }
        if (Date.class == param.getValue().getClass()) {
            return cb.greaterThan(root.get(param.getName()).as(Date.class), (Date) param.getValue());
        }
        return cb.greaterThan(root.get(param.getName()).as(String.class), String.valueOf(param.getValue()));
    }

    private static <T> Predicate getGreaterThanOrEqualToPredicate(QueryParam param, Root<T> root, CriteriaBuilder cb) {
        if (Integer.class == param.getValue().getClass()) {
            return cb.greaterThanOrEqualTo(root.get(param.getName()).as(Integer.class), (int) param.getValue());
        }
        if (Long.class == param.getValue().getClass()) {
            return cb.greaterThanOrEqualTo(root.get(param.getName()).as(Long.class), (long) param.getValue());
        }
        if (Double.class == param.getValue().getClass()) {
            return cb.greaterThanOrEqualTo(root.get(param.getName()).as(Double.class), (double) param.getValue());
        }
        if (Float.class == param.getValue().getClass()) {
            return cb.greaterThanOrEqualTo(root.get(param.getName()).as(Float.class), (float) param.getValue());
        }
        if (Timestamp.class == param.getValue().getClass()) {
            return cb.greaterThanOrEqualTo(root.get(param.getName()).as(Timestamp.class), (Timestamp) param.getValue());
        }
        if (Date.class == param.getValue().getClass()) {
            return cb.greaterThanOrEqualTo(root.get(param.getName()).as(Date.class), (Date) param.getValue());
        }
        return cb.lessThanOrEqualTo(root.get(param.getName()).as(String.class), String.valueOf(param.getValue()));
    }
}
