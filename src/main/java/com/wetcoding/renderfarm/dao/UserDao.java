package com.wetcoding.renderfarm.dao;

import com.wetcoding.renderfarm.models.User;
import com.wetcoding.renderfarm.utils.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import javax.jws.soap.SOAPBinding;
import javax.persistence.NoResultException;
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

    /**
     * Возвращет пользователя по параметрам
     * @param email - email пользователя
     * @param password - пароль пользователя
     * @return null-если такого пользователя не существует
     */
    public User getByParameters(String email, String password){
        Session session=HibernateUtil.getSession();
        String hql="from User u where u.email= :email and u.password= :password";
        Query<User> query=session.createQuery(hql,User.class);
        query.setParameter("email",email);
        query.setParameter("password",password);
        User user=null;
        try{
            user=query.getSingleResult();
        } catch (NoResultException e){

        }
        return user;
    }

    /**
     * Возвращает пользователя по email
     * @param email - email пользователя
     * @return null-если такого пользователя не существует
     */
    public User getByEmail(String email){
        Session session=HibernateUtil.getSession();
        String hql="from User u where u.email= :email";
        Query<User> query=session.createQuery(hql,User.class);
        query.setParameter("email",email);
        User user=null;
        try{
            user=query.getSingleResult();
        } catch (NoResultException e){

        }
        return user;
    }

    public List<User> getAll(){
        Session session = HibernateUtil.getSession();
        List<User> users = session.createQuery("from User").getResultList();
        return users;
    }
}
