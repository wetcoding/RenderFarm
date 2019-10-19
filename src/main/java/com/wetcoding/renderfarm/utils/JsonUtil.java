package com.wetcoding.renderfarm.utils;

import com.wetcoding.renderfarm.models.Task;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Вспомогательный класс для работы с JSON
 */
public class JsonUtil {
    StringBuilder stringBuilder;
    JSONObject jsonObject;
    private static final Logger log = Logger.getLogger(JsonUtil.class.getName());

    public JsonUtil(){

    }

    public JsonUtil(HttpServletRequest req) throws JSONException {
        stringBuilder= new StringBuilder ();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                stringBuilder.append(line);
        } catch (IOException e) {
            log.log(Level.SEVERE,"Exception while reading from request",e);
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
