package com.demo.udema.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "lessons")
public class Lessons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "length")
    private Time length;

    @Column(name = "description")
    private String description;

    public Lessons()  {}

    public Lessons(int id, String name, Time length, String description) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.description = description;
    }

    public Lessons(String name, Time length, String description) {
        this.name = name;
        this.length = length;
        this.description = description;
    }
}
