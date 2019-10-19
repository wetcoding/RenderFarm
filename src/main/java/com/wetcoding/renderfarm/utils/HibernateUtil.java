package com.wetcoding.renderfarm.utils;

import com.wetcoding.renderfarm.models.Task;
import com.wetcoding.renderfarm.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;


import java.util.Objects;
import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static Session session;

    public static SessionFactory getSessionFactory(){
        if(Objects.isNull(sessionFactory)){
            try{
                Configuration configuration=new Configuration();
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                properties.put(Environment.URL, "jdbc:mysql://localhost:3306/renderfarm?verifyServerCertificate=false&useSSL=false&allowPublicKeyRetrieval=true&requireSSL=false&useLegacyDatetimeCode=false&amp&serverTimezone=UTC");
                properties.put(Environment.USER, "root");
                properties.put(Environment.PASS, "root");
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                properties.put(Environment.SHOW_SQL, "true");

                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Task.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e){
                System.out.println("Exception while creating session factory: "+e.getMessage());
            }
        }

        return sessionFactory;
    }

    public static Session getSession(){
        if(Objects.isNull(session) || !session.isOpen()){
            session=getSessionFactory().openSession();
            System.out.println("Open session");
        }

        return session;
    }

    public static void closeSession(){
        if(Objects.nonNull(session) && session.isOpen()){
            session.close();
            System.out.println("Session closed");
        }
    }


}
