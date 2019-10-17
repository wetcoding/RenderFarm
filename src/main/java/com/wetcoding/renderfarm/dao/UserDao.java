package com.wetcoding.renderfarm.dao;

import com.wetcoding.renderfarm.models.User;
import com.wetcoding.renderfarm.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDao {

    public void save(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    public  void update(User user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(user);
        transaction.commit();
        session.close();
    }

    public User get(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user= session.get(User.class, id);
        return user;
    }

    public List<User> getAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> users = session.createQuery("FROM User").getResultList();
        //session.close();//!!
        return users;
    }
}
