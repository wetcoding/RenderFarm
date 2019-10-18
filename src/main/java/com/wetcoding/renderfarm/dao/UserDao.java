package com.wetcoding.renderfarm.dao;

import com.wetcoding.renderfarm.models.User;
import com.wetcoding.renderfarm.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * DAO класс для пользователей
 */
public class UserDao {

    public void save(User user) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
    }

    public  void update(User user){
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.merge(user);
        transaction.commit();
    }

    public User get(int id){
        Session session = HibernateUtil.getSession();
        User user= session.get(User.class, id);
        return user;
    }

    public List<User> getAll(){
        Session session = HibernateUtil.getSession();
        List<User> users = session.createQuery("FROM User").getResultList();
        return users;
    }
}
