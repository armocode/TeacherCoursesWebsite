package com.demo.udema.entity;

import javax.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "data")
    private String data;

    @ManyToOne()
    @JoinColumn(name = "teacher_id") //FK
    private User users;

    @Column(name = "title")
    private String title;

    @OneToOne
    @JoinColumn(name = "category_id") //FK
    private Category category;

    @Column(name = "price")
    private double price;

    public Course() {}

    public Course(int id, String data, User users, String title, Category category, double price) {
        this.id = id;
        this.data = data;
        this.users = users;
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public Course(String data, User users, String title, Category category, double price) {
        this.data = data;
        this.users = users;
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public Course(User users, String title, Category category, double price) {
        this.users = users;
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public Course(String title, Category category, double price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
