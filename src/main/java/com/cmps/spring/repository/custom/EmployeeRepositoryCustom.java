package com.cmps.spring.repository.custom;

import java.util.List;
import com.cmps.spring.entity.Employee;

public interface EmployeeRepositoryCustom {
	/**
	 * 従業員テーブルからの動的な複数条件AND検索
	 * 
	 * @param name String 名前の検索キーワード
	 * @param ageLower String 年齢の下限
	 * @param ageUpper String 年齢の上限
	 * @return List<Employee>
	 */
	public List<Employee> search(String name, Integer ageLower, Integer ageUpper);
}