package com.wetcoding.renderfarm.services;

import com.wetcoding.renderfarm.dao.TaskDao;
import com.wetcoding.renderfarm.dao.UserDao;
import com.wetcoding.renderfarm.models.Task;
import com.wetcoding.renderfarm.models.User;

import java.util.List;
import java.util.Objects;

public class FarmService {
    UserDao userDao=new UserDao();
    //TaskDao taskDao=new TaskDao();

    public int login(String email, String password){
        List<User> users=userDao.getAll();
        for(User user:users){
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                return user.getId();
            }
        }
        return -1;
    }

    public boolean register(String email, String password){
        List<User> users=userDao.getAll();
        for(User user:users){
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                return false;
            }
        }
        userDao.save(new User(email,password));
        return true;
    }

    public List<Task> addTask(int userId, String taskName){
        User user=userDao.get(userId);
        if(Objects.nonNull(user)) {
            Task task = new Task(0, taskName);
            user.addTask(task);
            userDao.update(user);
            return user.getTasks();
        }
        return null;
    }

    public List<Task> getTasks(int userId){
        List<Task> tasks=null;
        User user=userDao.get(userId);
        if(Objects.nonNull(user)){
            tasks=user.getTasks();
        }
        return tasks;
    }
}
