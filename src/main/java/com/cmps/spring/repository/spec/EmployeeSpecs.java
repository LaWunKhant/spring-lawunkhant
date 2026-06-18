package com.cmps.spring.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import com.cmps.spring.entity.Employee;

public class EmployeeSpecs {

    /** 1. Name partial match search (LIKE %name%) */
    public static Specification<Employee> nameContains(String name) {
        return (root, query, cb) -> 
            StringUtils.hasText(name) ? cb.like(root.get("name"), "%" + name + "%") : null;
    }

    /** 2. Age lower limit (>=) */
    public static Specification<Employee> ageGreaterThanEqual(Integer ageLower) {
        return (root, query, cb) -> 
            (ageLower != null) ? cb.greaterThanOrEqualTo(root.get("age"), ageLower) : null;
    }

    /** 3. Age upper limit (<=) */
    public static Specification<Employee> ageLessThanEqual(Integer ageUpper) {
        return (root, query, cb) -> 
            (ageUpper != null) ? cb.lessThanOrEqualTo(root.get("age"), ageUpper) : null;
    }

    /** 4. EXERCISE TASK: Code partial match search (LIKE %code%) */
    public static Specification<Employee> codeContains(String code) {
        return (root, query, cb) -> 
            StringUtils.hasText(code) ? cb.like(root.get("code"), "%" + code + "%") : null;
    }
}