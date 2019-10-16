package com.wetcoding.renderfarm.models;

import javax.persistence.*;

@Entity
@Table (name="tasks")
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
}
