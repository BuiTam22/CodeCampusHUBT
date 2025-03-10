package com.codecampushubt.NCKH2024TQQD.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Calls")
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Thêm trường id làm khóa chính
}
