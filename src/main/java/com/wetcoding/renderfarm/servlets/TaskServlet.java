package com.wetcoding.renderfarm.servlets;

import com.wetcoding.renderfarm.models.Task;
import com.wetcoding.renderfarm.services.FarmService;
import com.wetcoding.renderfarm.utils.JsonUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet (urlPatterns = "/task")
public class TaskServlet extends HttpServlet {
    FarmService farmService;
    @Override
    public void init() throws ServletException {
        super.init();
        farmService=new FarmService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            int id=Integer.valueOf(req.getParameter("id"));

            List<Task> taskList=farmService.getTasks(id);
            if(Objects.nonNull(taskList)){
                JSONObject jsonResponse=new JsonUtil().taskListToJson(taskList);
                resp.getWriter().println(jsonResponse.toString());
            }
        } catch (NumberFormatException e) {
            System.out.println("Error while parsing get parameter");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            JsonUtil jsonUtil=new JsonUtil(req);
            int id=jsonUtil.getInt("id");
            List<Task> taskList=farmService.addTask(id,"User "+id+" task");
            if(Objects.nonNull(taskList)){
                JSONObject jsonResponse=new JsonUtil().taskListToJson(taskList);
                resp.getWriter().println(jsonResponse.toString());
            }
        } catch (JSONException e) {
            System.out.println("Error while parsing JSON");
        }
    }
}
