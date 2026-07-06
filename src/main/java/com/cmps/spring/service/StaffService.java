package com.cmps.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cmps.spring.entity.Staff;
import com.cmps.spring.repository.StaffRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StaffService {
    private final StaffRepository staffRepository;

    /**
     * 全スタッフを取得
     */
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    /**
     * IDでスタッフを取得
     */
    public Staff findById(Long id) {
        return staffRepository.findById(id).orElse(null);
    }

    /**
     * スタッフを保存
     */
    public void save(Staff staff) {
        staffRepository.save(staff);
    }

    /**
     * スタッフを削除
     */
    public void deleteById(Long id) {
        staffRepository.deleteById(id);
    }
}