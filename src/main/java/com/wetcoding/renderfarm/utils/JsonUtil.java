package com.wetcoding.renderfarm.utils;

import com.wetcoding.renderfarm.models.Task;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.List;
import java.util.Objects;

public class JsonUtil {
    StringBuilder stringBuilder;
    JSONObject jsonObject;

    public JsonUtil(){

    }

    public JsonUtil(HttpServletRequest req) throws JSONException {
        stringBuilder= new StringBuilder ();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                stringBuilder.append(line);
        } catch (Exception e) {
            System.out.println(e.getMessage());//!!!
            return;
        }
        jsonObject =  new JSONObject(stringBuilder.toString());
    }

    public String getString(String key) throws JSONException{
        if(Objects.nonNull(jsonObject)){
            return jsonObject.getString(key);
        }
        throw new JSONException("JSON object is null");
    }

    public int getInt(String key) throws JSONException{
        if(Objects.nonNull(jsonObject)){
            return jsonObject.getInt(key);
        }
        throw new JSONException("JSON object is null");
    }

    public JSONObject taskListToJson(List<Task> taskList){
        JSONObject jsonResponse= new JSONObject();
        JSONArray jsonArray=new JSONArray();

        for(Task task:taskList){
            JSONObject obj = new JSONObject();
            obj.put("id", task.getId());
            obj.put("name", task.getName());
            obj.put("status", task.getStatus());
            obj.put("startTime", task.getStartTime());
            jsonArray.put(obj);
        }

        jsonResponse.put("tasks", jsonArray);
        return jsonResponse;
    }
}
