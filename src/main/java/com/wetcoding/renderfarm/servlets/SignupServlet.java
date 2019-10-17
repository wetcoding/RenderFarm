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
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet (urlPatterns = "/signup")
public class SignupServlet extends HttpServlet {
    FarmService farmService;
    @Override
    public void init() throws ServletException {
        super.init();
        farmService=new FarmService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean signup=false;
        try {
            JsonUtil jsonUtil=new JsonUtil(req);
            String email=jsonUtil.getString("email");
            String password=jsonUtil.getString("password");
            signup=farmService.register(email,password);
        } catch (JSONException e) {
            System.out.println("Error while parsing JSON");
        }

        JSONObject jsonResponse= new JSONObject();
        jsonResponse.put("status",signup?"OK":"ERROR");

        resp.getWriter().println(jsonResponse.toString());

    }
}
