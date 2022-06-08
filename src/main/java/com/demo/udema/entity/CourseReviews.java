package com.demo.udema.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "course_reviews")
public class CourseReviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "user_id") //FK
    private User users;

    @ManyToOne()
    @JoinColumn(name = "course_details_id") //FK
    private CourseDetails courseDetails;

    @Column(name = "data")
    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

    @Column(name = "rating", columnDefinition = "double default 0")
    private Double rating;

    @Column(name = "review", nullable=true)
    private String review;

    @Value("#{new String('${orderReviews.latest}')}")
    @Transient
    public String latest ;

    @Value("#{new String('${orderReviews.oldest}')}")
    @Transient
    public String oldest;

    public CourseReviews() {
    }

    public CourseReviews(int id, User users, CourseDetails courseDetails, String timeStamp, double rating, String review) {
        this.id = id;
        this.users = users;
        this.courseDetails = courseDetails;
        this.timeStamp = timeStamp;
        this.rating = rating;
        this.review = review;
    }

    public CourseReviews(User users, CourseDetails courseDetails, String timeStamp, double rating, String review) {
        this.users = users;
        this.courseDetails = courseDetails;
        this.timeStamp = timeStamp;
        this.rating = rating;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public CourseDetails getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(CourseDetails courseDetails) {
        this.courseDetails = courseDetails;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

    public String getOldest() {
        return oldest;
    }

    public void setOldest(String oldest) {
        this.oldest = oldest;
    }

    @Override
    public String toString() {
        return "CourseReviews{" +
                "latest='" + latest + '\'' +
                ", oldest='" + oldest + '\'' +
                '}';
    }
}
