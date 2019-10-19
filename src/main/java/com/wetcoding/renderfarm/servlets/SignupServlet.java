package com.wetcoding.renderfarm.servlets;

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
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet (urlPatterns = "/signup")
public class SignupServlet extends HttpServlet {

    FarmService farmService;
    private static final Logger log = Logger.getLogger(SignupServlet.class.getName());

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
            log.log(Level.INFO,"Error while parsing JSON");
        }

        HibernateUtil.closeSession();
        JSONObject jsonResponse= new JSONObject();
        jsonResponse.put("status",signup?"OK":"ERROR");

        resp.getWriter().println(jsonResponse.toString());


    }
}
