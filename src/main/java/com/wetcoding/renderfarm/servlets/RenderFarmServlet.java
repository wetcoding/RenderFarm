package com.wetcoding.renderfarm.servlets;

import com.wetcoding.renderfarm.dao.UserDao;
import com.wetcoding.renderfarm.models.Task;
import com.wetcoding.renderfarm.models.User;
import com.wetcoding.renderfarm.services.FarmService;
import com.wetcoding.renderfarm.utils.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet (urlPatterns = "/")
public class RenderFarmServlet extends HttpServlet {
    FarmService farmService;
    @Override
    public void init() throws ServletException {
        super.init();
        farmService=new FarmService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();


        /*
        int result=farmService.login("secondPerson","pass");
        farmService.addTask(10,"Task for person 3");
        List<Task> tasks=farmService.getTasks(15);
        for(Task task:tasks){
            writer.println(task.getId()+"  "+task.getName());
        }
        */

        writer.println("Heelo");

    }


}
