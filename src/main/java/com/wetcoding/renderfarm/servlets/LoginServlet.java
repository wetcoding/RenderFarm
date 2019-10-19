package com.wetcoding.renderfarm.servlets;

import com.wetcoding.renderfarm.services.FarmService;
import com.wetcoding.renderfarm.utils.JsonUtil;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet (urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    FarmService farmService;

    @Override
    public void init() throws ServletException {
        super.init();
        farmService=new FarmService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id=-1;
        try {
            JsonUtil jsonUtil=new JsonUtil(req);
            String email=jsonUtil.getString("email");
            String password=jsonUtil.getString("password");
            id=farmService.login(email,password);
        } catch (JSONException e) {
            System.out.println("Error while parsing JSON");
        }

        JSONObject jsonResponse= new JSONObject();
        jsonResponse.put("status",id!=-1?"OK":"ERROR");
        jsonResponse.put("id",id);

        resp.getWriter().println(jsonResponse.toString());
    }
}
