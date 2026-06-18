package com.cmps.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "輸出先") // Maps directly to your DBeaver table name
public class ExportDestination {

    @Id
    @Column(name = "輸出先コード")
    private Integer code;

    @Column(name = "輸出先名")
    private String name;

    @Column(name = "人口")
    private Integer population;

    @Column(name = "地方")
    private String region;
}