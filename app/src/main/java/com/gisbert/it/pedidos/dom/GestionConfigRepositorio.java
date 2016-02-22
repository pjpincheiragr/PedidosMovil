package com.pluscel.pluscelmovil.dom;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class GestionConfigRepositorio{

    public GestionConfig recuperarConfiguracion(Activity activity){

        GestionConfig gestionConfig = new GestionConfig();

        String json = leerJSON(activity);

        try {

            JSONObject obj = new JSONObject(json);

            String urlRestful = obj.getString("urlRestful");
            String user = obj.getString("user");
            String pass = obj.getString("pass");
            String save = obj.getString("save");

            gestionConfig.setUrlRestful(urlRestful);
            gestionConfig.setUser(user);
            gestionConfig.setPass(pass);
            gestionConfig.setSave(Boolean.valueOf(save));


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return gestionConfig;
    }

    public void guardarConfiguracion(Activity activity, GestionConfig config){

        JSONObject obj = new JSONObject();
        try {
            obj.put("user", config.getUser());
            obj.put("pass", config.getPass());
            obj.put("urlRestful", config.getUrlRestful());
            obj.put("save", config.getSave().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v("JSON", obj.toString());

        try {
            FileOutputStream fos = activity.openFileOutput("config.json", Context.MODE_PRIVATE);
            fos.write(obj.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String leerJSON(Activity activity){

        String json = null;

        Activity a = new Activity();
        try {
            FileInputStream fis = activity.openFileInput("config.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            json = sb.toString();

        } catch (FileNotFoundException e) {
            json = "{ \"urlRestful\" : \"\",  \"user\" : \"\",  \"pass\" : \"\",  \"save\" : \"false\"}";
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;

    }
}
