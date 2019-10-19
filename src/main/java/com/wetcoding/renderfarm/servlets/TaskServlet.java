package com.wetcoding.renderfarm.servlets;

import com.wetcoding.renderfarm.models.Task;
import com.wetcoding.renderfarm.services.FarmService;
import com.wetcoding.renderfarm.utils.HibernateUtil;
import com.wetcoding.renderfarm.utils.JsonUtil;
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
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet (urlPatterns = "/task")
public class TaskServlet extends HttpServlet {

    FarmService farmService;
    private static final Logger log = Logger.getLogger(TaskServlet.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        farmService=new FarmService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            JsonUtil jsonUtil=new JsonUtil(req);
            int id=jsonUtil.getInt("id");
            String type=jsonUtil.getString("type");
            List<Task> taskList=null;
            if(type.equals("get")){
                taskList=farmService.getTasks(id);
            } else {
                farmService.addTask(id,"User "+id+" task");
            }
            if(Objects.nonNull(taskList)){
                JSONObject jsonResponse=new JsonUtil().taskListToJson(taskList);
                resp.getWriter().println(jsonResponse.toString());
            }
        } catch (JSONException e) {
            log.log(Level.INFO,"Error while parsing JSON");
        }
    }
}
