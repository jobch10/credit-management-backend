package com.project.creditmanagementbackend.app.util.pagination.util;

import com.project.creditmanagementbackend.app.util.pagination.model.Operator;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpecificationBuilder {

    private SpecificationBuilder() {
        throw new IllegalStateException("Utility class");
    }

    public static <E> Specification<E> build(Map<String, Object> filters, Map<String, Operator> allowedFilters) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters != null && allowedFilters != null) {
                filters.forEach((key, value) -> {
                    if (value != null && allowedFilters.containsKey(key)) {
                        Operator operator = allowedFilters.get(key);
                        predicates.add(buildPredicate(root, cb, key, value, operator));
                    }
                });
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Predicate buildPredicate(Root<?> root, CriteriaBuilder cb, String key, Object value,
            Operator operator) {
        Path<?> path = getPath(root, key);

        return switch (operator) {
            case EQ -> cb.equal(path, value);
            case LIKE -> cb.like(cb.lower(path.as(String.class)), "%" + value.toString().toLowerCase() + "%");
            case GT -> cb.greaterThan(path.as(String.class), value.toString());
            case LT -> cb.lessThan(path.as(String.class), value.toString());
            case GTE -> cb.greaterThanOrEqualTo(path.as(String.class), value.toString());
            case LTE -> cb.lessThanOrEqualTo(path.as(String.class), value.toString());
            case NOT_EQ -> cb.notEqual(path, value);
            case IN -> path.in((Object[]) value.toString().split(","));
        };
    }

    private static Path<?> getPath(Root<?> root, String key) {
        if (!key.contains(".")) {
            return root.get(key);
        }

        String[] parts = key.split("\\.");
        From<?, ?> from = root;
        for (int i = 0; i < parts.length - 1; i++) {
            from = from.join(parts[i]);
        }
        return from.get(parts[parts.length - 1]);
    }
}
