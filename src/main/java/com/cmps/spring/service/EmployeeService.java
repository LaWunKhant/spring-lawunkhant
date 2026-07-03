package com.cmps.spring.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.cmps.spring.entity.Employee;
import com.cmps.spring.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
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
    
    public void doTransactionSample(Long delId) {

		// delIdを元にEntityを取得し、削除する
		Employee employee1 = employeeRepository.findById(delId).get();//※①Entityが存在しない場合、エラーになる
		employeeRepository.delete(employee1);
		
		//用意したインスタンスをDBに新規登録
		Employee employee2 = new Employee("00800", "日下部", 46);//※②codeカラムは4文字が上限のため、"00800"の場合SQL実行時に必ずエラーになる
		employeeRepository.save(employee2);
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
    
    public Page<Employee> findAllPaginated(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }
    
    
}