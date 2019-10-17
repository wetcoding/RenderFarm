package com.wetcoding.renderfarm.models;

import javax.persistence.*;

@Entity
@Table (name="task")
public class Task {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    int id;

    private long startTime;
    private String name;
    private String status;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    public Task(){

    }
    public Task(long startTime, String name) {
        this.startTime = startTime;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
