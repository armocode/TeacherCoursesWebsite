package com.demo.udema.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "course_reviews")
public class CourseReviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "data")
    private Date date;

    @OneToOne
    @JoinColumn(name = "user_id") //FK
    private User users;

    @Column(name = "rating")
    private double rating;

    @Column(name = "review")
    private String review;

    public CourseReviews() {
    }

    public CourseReviews(int id, Date date, User users, double rating, String review) {
        this.id = id;
        this.date = date;
        this.users = users;
        this.rating = rating;
        this.review = review;
    }

    public CourseReviews(Date date, User users, double rating, String review) {
        this.date = date;
        this.users = users;
        this.rating = rating;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
