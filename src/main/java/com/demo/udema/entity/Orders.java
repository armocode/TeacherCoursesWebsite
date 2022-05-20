package com.demo.udema.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue
    @Column(name = "user_id")  // PK
    private int userId;

    @Column(name = "course_id") // FK
    private int courseId;

    @Column(name = "data")
    private Date date;

    @Column(name = "price")
    private double price;

    @Column(name = "certificate_url", length = 512)
    private String certificate_url;

public Orders() {}

    public Orders(int userId, int courseId, Date date, double price, String certificate_url) {
        this.userId = userId;
        this.courseId = courseId;
        this.date = date;
        this.price = price;
        this.certificate_url = certificate_url;
    }

    public Orders(int courseId, Date date, double price, String certificate_url) {
        this.courseId = courseId;
        this.date = date;
        this.price = price;
        this.certificate_url = certificate_url;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCertificate_url() {
        return certificate_url;
    }

    public void setCertificate_url(String certificate_url) {
        this.certificate_url = certificate_url;
    }
}
