package com.cmps.spring.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.cmps.spring.entity.Employee;
import com.cmps.spring.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * ユーザー全件を取得 (ソート付き)
     */
    public List<Employee> findAll() {
        return employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
    
    /**
     * 名前で検索 (問1-2)
     */
    public List<Employee> findByName(String name) {
        return employeeRepository.findByName(name);
    }

    /**
     * 平均年齢を取得 (問1-3)
     */
    public Double getAverageAge() {
        return employeeRepository.getAverageAge();
    }

    /**
     * 社員情報をそのまま保存する (追加/更新ボタン用)
     */
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    /**
     * IDで社員を削除する
     */
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    /**
     * 複数条件で検索する (動的クエリ用)
     */
    public List<Employee> search(String name, Integer ageLower, Integer ageUpper) {
        return employeeRepository.search(name, ageLower, ageUpper);
    }
	
    /**
     * 受け取ったidのデータが存在すれば更新、しなければ新規登録 (元の練習メソッド)
     */
    public Employee saveIfUnique(Long id, String name) {
        boolean exists = employeeRepository.existsById(id);
        Employee emp = !exists ? new Employee() : employeeRepository.findById(id).get();
        emp.setName(name);
        return employeeRepository.save(emp);
    }
}