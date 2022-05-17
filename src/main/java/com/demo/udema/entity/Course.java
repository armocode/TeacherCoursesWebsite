package com.demo.udema.entity;

import javax.persistence.*;

@Entity
@Table(name = "kursai")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "data")
    private String data;

    @ManyToOne()
    @JoinColumn(name = "mokytojo_id") //FK
    private User users;

    @Column(name = "pavadinimas")
    private String title;

    @OneToOne
    @JoinColumn(name = "kategorijos_id") //FK
    private Category category;

    @Column(name = "kaina")
    private double price;
}
