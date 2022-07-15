package com.practice.houseutils.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Apartment { // 자료구조

    // 통상적으로 자바는 게터세터로 접근
    // 코틀린은 public으로 외부에 노출한다고 함

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String address;

    @Column(nullable = false)
    public Long price;

}
