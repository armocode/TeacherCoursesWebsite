package com.demo.udema.entity;

import javax.persistence.*;

@Entity
@Table(name = "kategorija")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "pavadinimas")
    private String title;

    @OneToOne(mappedBy = "category", cascade = CascadeType.ALL)
    private Course course;
}
