package com.cmps.spring.repository.custom.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import com.cmps.spring.entity.Employee;
import com.cmps.spring.repository.custom.EmployeeRepositoryCustom;

public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {

	// Entityを利用するために必要な機能を提供する
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Employee> search(String name, Integer ageLower, Integer ageUpper) {
		//StringBuilderでSQL文を連結する
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT em FROM Employee em WHERE 1=1");

		// 各項目が入力されているかどうかの真偽値を収めた変数
		boolean nameExists = !"".equals(name) && name != null;
		boolean ageLowerExists = ageLower != null;
		boolean ageUpperExists = ageUpper != null;

		//各カラムがブランクではなかった場合、sql変数にappendする
		if (nameExists) {
			builder.append(" AND em.name LIKE :name");
		}
		if (ageLowerExists) {
			builder.append(" AND em.age >= :ageLower");
		}
		if (ageUpperExists) {
			builder.append(" AND em.age <= :ageUpper");
		}
		
		// 整列
		builder.append(" order by em.id");

		/*
		QueryはSQLでデータを問い合わせるためのクエリ文に相当する機能を持つ
		entityManagerのcreateQueryメソッドを使用する
		sql変数を引数に渡す
		*/
		Query query = entityManager.createQuery(builder.toString());

		// 各変数に値をセットする
		if (nameExists) query.setParameter("name", "%" + name + "%");
		if (ageLowerExists) {
			query.setParameter("ageLower", ageLower);
		}
		if (ageUpperExists) {
			query.setParameter("ageUpper", ageUpper);
		}

		return query.getResultList();
	}

}