package com.cmps.spring.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import com.cmps.spring.entity.Employee;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @Test
    @Sql("/schema-emoloyee.sql")
    @DisplayName("名前が一致するレコードを取得する")
    void testFindByNameEquals() throws Exception {

        Employee expected = new Employee("0900", "秋葉", 30);
        repository.save(expected);

        Employee employee = repository.findByNameEquals("秋葉").orElse(null);

        assertNotNull(employee);
        assertEquals(expected, employee);
    }

    @Test
    @Sql({ "/schema-emoloyee.sql", "/data-emoloyee.sql" })
    @DisplayName("名前を含むリストを取得する - 取得件数の一致を確認")
    void testFindByNameContaining() throws Exception {

        List<Employee> list = repository.findByNameContaining("田中");

        assertThat(list.size()).isEqualTo(2);
    }
}