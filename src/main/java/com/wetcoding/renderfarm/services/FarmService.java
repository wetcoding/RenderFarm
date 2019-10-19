package com.wetcoding.renderfarm.services;

import com.wetcoding.renderfarm.dao.UserDao;
import com.wetcoding.renderfarm.models.Task;
import com.wetcoding.renderfarm.models.User;
import com.wetcoding.renderfarm.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс для реализации логики
 */
public class FarmService {

    private static final Logger log = Logger.getLogger(FarmService.class.getName());
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
        Session session=HibernateUtil.getSessionFactory().openSession();
        User user=userDao.getByParameters(session,email,password);
        session.close();
        if(Objects.nonNull(user)){
            log.log(Level.INFO,"User login "+email);
            return user.getId();
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
        Session session=HibernateUtil.getSessionFactory().openSession();
        User user=userDao.getByParameters(session, email);
        boolean operationResult=false;
        if(Objects.isNull(user)){
            userDao.save(session,new User(email,password));
            operationResult=true;
            log.log(Level.INFO,"User registered "+email);
        }
        session.close();
        return operationResult;
    }

    /**
     * Добавление задачи пользователя
     * @param userId - id пользователя
     * @param taskName - имя задачи
     * @return - true, если задача добавлена
     */
    public boolean addTask(int userId, String taskName){
        Session session=HibernateUtil.getSessionFactory().openSession();
        User user=userDao.get(session, userId);
        boolean operationResult=false;
        if(Objects.nonNull(user)) {
            Task task = new Task(System.currentTimeMillis(), taskName);
            user.addTask(task);
            userDao.update(session, user);
            operationResult=true;
            log.log(Level.INFO,"User "+user.getEmail()+" add task");
        }
        session.close();
        return operationResult;
    }

    /**
     * Возвращает список задач пользователя и проверяет
     * статус задачи на выполнение
     * @param userId - id пользователя
     * @return - null, если пользователя с таким id не существует
     */
    public List<Task> getTasks(int userId){
        Session session=HibernateUtil.getSessionFactory().openSession();
        List<Task> tasks=null;
        User user=userDao.get(session, userId);
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
                userDao.update(session, user);
            }
        }
        session.close();
        return tasks;
    }
}
