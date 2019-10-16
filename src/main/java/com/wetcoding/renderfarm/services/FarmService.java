package com.wetcoding.renderfarm.services;

import com.wetcoding.renderfarm.models.Task;

import java.util.List;

public class FarmService {

    public int login(String email, String password){
        return 0;
    }

    public boolean register(String email, String password){
        return true;
    }

    public boolean addTask(int userId, String taskName){
        return true;
    }

    public List<Task> getTasks(int user_id){
        return null;
    }
}
