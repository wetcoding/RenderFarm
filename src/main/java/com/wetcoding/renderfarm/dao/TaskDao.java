package com.wetcoding.renderfarm.dao;

import com.wetcoding.renderfarm.models.Task;
import com.wetcoding.renderfarm.models.User;
import com.wetcoding.renderfarm.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TaskDao {
    /*
    public void save(Task task){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        session.save(task);
        tx1.commit();
        //session.close();
    }

    public List<Task> getAll(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<Task> tasks = session.createQuery("FROM Task").getResultList();
        //session.close();
        return tasks;
    }
    */

}
