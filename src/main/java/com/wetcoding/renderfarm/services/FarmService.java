package com.wetcoding.renderfarm.services;

import com.wetcoding.renderfarm.dao.UserDao;
import com.wetcoding.renderfarm.models.Task;
import com.wetcoding.renderfarm.models.User;

import java.util.List;
import java.util.Objects;

/**
 * Класс для реализации логики
 */
public class FarmService {

    UserDao userDao=new UserDao();

    /** Время рендеринга в миллисекундах (3 мин)*/
    private static final long RENDER_TIME=180000;

    /**
     * Логин пользователя
     * @param email - email пользователя
     * @param password - пароль пользователя
     * @return - уникальный id, -1 если вход не удался
     */
    public int login(String email, String password){
        List<User> users=userDao.getAll();
        for(User user:users){
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                return user.getId();
            }
        }
        return -1;
    }

    /**
     * Регистрация пользователя
     * @param email - email пользователя
     * @param password - пароль пользователя
     * @return - true, если регистрация проша успешно
     */
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

    /**
     * Добавление задачи пользователя
     * @param userId - id пользователя
     * @param taskName - имя задачи
     * @return - true, если задача добавлена
     */
    public boolean addTask(int userId, String taskName){
        User user=userDao.get(userId);
        if(Objects.nonNull(user)) {
            Task task = new Task(System.currentTimeMillis(), taskName);
            user.addTask(task);
            userDao.update(user);
            return true;
        }
        return false;
    }

    /**
     * Возвращает список задач пользователя и проверяет
     * статус задачи на выполнение
     * @param userId - id пользователя
     * @return - null, если пользователя с таким id не существует
     */
    public List<Task> getTasks(int userId){
        List<Task> tasks=null;
        User user=userDao.get(userId);
        if(Objects.nonNull(user)){
            tasks=user.getTasks();
            boolean stateChanged=false;
            for(Task task:tasks){
                if(task.getStatus().equals("RENDERING") && System.currentTimeMillis()-task.getStartTime()>RENDER_TIME){
                    task.setStatus("COMPLETE");
                    stateChanged=true;
                }
            }
            if(stateChanged){
                userDao.update(user);
            }
        }
        return tasks;
    }
}
